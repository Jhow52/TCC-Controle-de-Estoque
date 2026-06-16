package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.ProductCreateNotFoundException;
import com.claretiano.estoque.handler.ProductNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.repository.ProductRepository;
import com.claretiano.estoque.response.ProductResponseDTO;
import com.claretiano.estoque.service.CategoryService;
import com.claretiano.estoque.service.ProductService;
import com.claretiano.estoque.request.ProductRequestDTO;
import com.claretiano.estoque.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public ProductResponseDTO findProductById(Long id) {
        return productRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ProductNotFoundException("The product with id " + id + " was not found"));
    }

    @Override
    public List<ProductResponseDTO> findByName(String name) {
        List<Product> productList = productRepository.findAllByNameContainingIgnoreCase(name);

        if(productList.isEmpty()){
            throw new ProductNotFoundException("Product " + name + " not found");
        }

       return productList.stream()
               .map(this::toResponseDTO)
               .toList();
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productDTO) {

        Category category = categoryService.findByName(
                productDTO.getCategoryName()
        );

        Product product = toEntity(productDTO,category);
        Product productSaved = productRepository.save(product);
        return toResponseDTO(productSaved);
    }

    @Override
    public List<ProductResponseDTO> listAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("The product with id " + id + " was not found"));

        Category category = categoryService.findByName(productDTO.getCategoryName());

        update(product, productDTO, category);
        Product productSaved = productRepository.save(product);
        return toResponseDTO(productSaved);
    }

    @Override
    public ProductResponseDTO removeProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("The product with id " + id + " was not found"));
        productRepository.delete(product);
        return toResponseDTO(product);
    }

    private void update(Product product, ProductRequestDTO productRequestDTO,Category category){
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setMinStock(productRequestDTO.getMinStock());
        product.setCategory(category);
    }

    private Product toEntity(ProductRequestDTO productDTO, Category category){
        return Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .minStock(productDTO.getMinStock())
                .category(category)
                .build();
    }

    private ProductResponseDTO toResponseDTO(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .minStock(product.getMinStock())
                .categoryName(product.getCategory().getName())
                .lowStock(product.getQuantity() < product.getMinStock())
                .build();
    }
}