package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.InventoryNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.repository.CategoryRepository;
import com.claretiano.estoque.repository.ProductRepository;
import com.claretiano.estoque.response.InventoryResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class InventoryServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldListAllInventory() {

        Product product = createProduct();

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        List<InventoryResponseDTO> result =
                inventoryService.listAllInventory();

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindProductsByName() {

        Product product = createProduct();

        when(productRepository
                .findAllByNameContainingIgnoreCase("Note"))
                .thenReturn(List.of(product));

        List<InventoryResponseDTO> result =
                inventoryService.findByName("Note");

        assertEquals(1, result.size());

        assertEquals(
                "Notebook",
                result.getFirst().getProductName()
        );
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundByName() {

        when(productRepository
                .findAllByNameContainingIgnoreCase("Teste"))
                .thenReturn(Collections.emptyList());

        assertThrows(
                InventoryNotFoundException.class,
                () -> inventoryService.findByName("Teste")
        );
    }

    @Test
    void shouldFindProductById() {

        Product product = createProduct();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        InventoryResponseDTO result =
                inventoryService.findById(1L);

        assertEquals(
                "Notebook",
                result.getProductName()
        );
    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                InventoryNotFoundException.class,
                () -> inventoryService.findById(1L)
        );
    }

    @Test
    void shouldFindProductsByCategory() {

        Product product = createProduct();

        Category category = Category.builder()
                .id(1L)
                .name("Informática")
                .products(List.of(product))
                .build();

        when(categoryRepository.findByNameNormalized(anyString()))
                .thenReturn(Optional.of(category));

        List<InventoryResponseDTO> result =
                inventoryService.findByCategory("Informática");

        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {

        when(categoryRepository.findByNameNormalized(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(
                InventoryNotFoundException.class,
                () -> inventoryService.findByCategory("Teste")
        );
    }

    @Test
    void shouldReturnLowStockProducts() {

        Category category = Category.builder()
                .name("Informática")
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("Notebook")
                .quantity(2)
                .minStock(5)
                .category(category)
                .build();

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        List<InventoryResponseDTO> result =
                inventoryService.findLowStockProducts();

        assertEquals(1, result.size());

        assertTrue(result.getFirst().getLowStock());
    }

    @Test
    void shouldReturnEmptyWhenNoLowStockProducts() {

        Category category = Category.builder()
                .name("Informática")
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("Notebook")
                .quantity(20)
                .minStock(5)
                .category(category)
                .build();

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        List<InventoryResponseDTO> result =
                inventoryService.findLowStockProducts();

        assertTrue(result.isEmpty());
    }

    private Product createProduct() {

        Category category = Category.builder()
                .id(1L)
                .name("Informática")
                .build();

        return Product.builder()
                .id(1L)
                .name("Notebook")
                .quantity(10)
                .minStock(5)
                .category(category)
                .build();
    }

}