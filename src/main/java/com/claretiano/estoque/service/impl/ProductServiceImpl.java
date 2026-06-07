package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.ProductNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.repository.ProductRepository;
import com.claretiano.estoque.response.ProductResponseDTO;
import com.claretiano.estoque.service.CategoryService;
import com.claretiano.estoque.service.ProductService;
import com.claretiano.estoque.request.ProductRequestDTO;
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
    public ProductResponseDTO buscarProdutoPorId(Long id) {
        return productRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public ProductResponseDTO criarProduto(ProductRequestDTO productDTO) {

        Category category = categoryService.buscarPorNome(
                productDTO.getCategoryName()
        );

        Product product = toEntity(productDTO,category);

        Product productSalvo = productRepository.save(product);
        return toResponseDTO(productSalvo);
    }

    @Override
    public List<ProductResponseDTO> listarProdutos() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO atualizar(Long id, ProductRequestDTO productDTO) {
        Product produtosExistente = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        produtosExistente.setName(productDTO.getName());
        produtosExistente.setDescription(productDTO.getDescription());
        produtosExistente.setPrice(productDTO.getPrice());
        produtosExistente.setQuantity(productDTO.getQuantity());
        produtosExistente.setMinStock(productDTO.getMinStock());
        Category category = categoryService.buscarPorNome(productDTO.getCategoryName());
        produtosExistente.setCategory(category);

        Product productAtualizado = productRepository.save(produtosExistente);
        return toResponseDTO(productAtualizado);
    }

    @Override
    public ProductResponseDTO remover(Long id) {
        Product produtosExistente = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(produtosExistente);
        return toResponseDTO(produtosExistente);
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