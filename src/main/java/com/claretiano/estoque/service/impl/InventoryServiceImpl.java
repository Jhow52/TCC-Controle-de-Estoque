package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.InventoryNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.repository.CategoryRepository;
import com.claretiano.estoque.repository.ProductRepository;
import com.claretiano.estoque.response.InventoryResponseDTO;
import com.claretiano.estoque.service.InventoryService;
import com.claretiano.estoque.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public InventoryServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<InventoryResponseDTO> listAllInventory() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<InventoryResponseDTO> findByName(String name) {
        List<Product> products = productRepository.findAllByNameContainingIgnoreCase(name);

        if(products.isEmpty()){
            throw new InventoryNotFoundException("Product " + name + " not found in the inventory");
        }

        return products.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public InventoryResponseDTO findById(Long id) {
        return productRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new InventoryNotFoundException("The product with id " + id + " was not found"));
    }

    @Override
    public List<InventoryResponseDTO> findByCategory(String category) {
        Category foundCategory = categoryRepository.findByNameNormalized(StringUtils.normalize(category))
                .orElseThrow(() -> new InventoryNotFoundException("Category " + category + " was not found in the inventory"));

        return foundCategory.getProducts()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<InventoryResponseDTO> findLowStockProducts() {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getQuantity() <= product.getMinStock())
                .map(this::toResponseDTO)
                .toList();
    }

    private InventoryResponseDTO toResponseDTO(Product product){
        return InventoryResponseDTO.builder()
                .id(product.getId())
                .productName(product.getName())
                .quantity(product.getQuantity())
                .minStock(product.getMinStock())
                .categoryName(product.getCategory().getName())
                .lowStock(product.getQuantity() <= product.getMinStock())
                .date(product.getCreatedAt())
                .build();
    }
}