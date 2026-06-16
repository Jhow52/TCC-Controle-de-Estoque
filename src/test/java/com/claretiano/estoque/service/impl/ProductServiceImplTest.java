package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.CategoryCreateNotFoundException;
import com.claretiano.estoque.handler.ProductNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.repository.ProductRepository;
import com.claretiano.estoque.request.ProductRequestDTO;
import com.claretiano.estoque.response.ProductResponseDTO;
import com.claretiano.estoque.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFindProductById(){

        Product product = createProduct();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.findProductById(1L);
        assertEquals("Notebook", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound(){

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,() -> productService.findProductById(1L));
    }

    @Test
    void shouldFindProductsByName(){
        Product product = createProduct();

        when(productRepository.findAllByNameContainingIgnoreCase("Note")).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.findByName("Note");

        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenProductNameNotFound(){

        when(productRepository.findAllByNameContainingIgnoreCase("Teste")).thenReturn(Collections.emptyList());

        assertThrows(ProductNotFoundException.class,() -> productService.findByName("Teste"));
    }

    @Test
    void shouldCreateProduct(){
        ProductRequestDTO dto =
                ProductRequestDTO.builder()
                        .name("Notebook")
                        .description("Dell")
                        .price(BigDecimal.valueOf(3000))
                        .quantity(10)
                        .minStock(2)
                        .categoryName("Informática")
                        .build();

        Category category = Category.builder()
                .id(1L)
                .name("Informática")
                .build();

        Product product = createProduct();

        when(categoryService.findByName("Informática")).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.createProduct(dto);

        assertEquals("Notebook", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound(){
        ProductRequestDTO dto =
                ProductRequestDTO.builder()
                        .categoryName("Inexistente")
                        .build();

        when(categoryService.findByName("Inexistente")).thenThrow(new CategoryCreateNotFoundException("Not Found"));

        assertThrows(CategoryCreateNotFoundException.class,() -> productService.createProduct(dto));
    }

    @Test
    void shouldListAllProducts(){
        Product product = createProduct();

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.listAllProducts();

        assertEquals(1, result.size());
    }

    @Test
    void shouldUpdateProduct(){
        ProductRequestDTO dto =
                ProductRequestDTO.builder()
                        .name("Notebook Atualizado")
                        .description("Nova descrição")
                        .price(BigDecimal.valueOf(5000))
                        .quantity(20)
                        .minStock(5)
                        .categoryName("Informática")
                        .build();

        Category category = Category.builder()
                .name("Informática")
                .build();

        Product product = createProduct();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryService.findByName("Informática")).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponseDTO result = productService.updateProduct(1L, dto);

        assertEquals("Notebook Atualizado", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingProductNotFound(){
        ProductRequestDTO dto =
                ProductRequestDTO.builder()
                        .build();

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,() -> productService.updateProduct(1L, dto));
    }

    @Test
    void shouldRemoveProduct(){
        Product product = createProduct();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.removeProduct(1L);

        verify(productRepository).delete(product);

        assertEquals("Notebook", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenRemovingProductNotFound(){

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,() -> productService.removeProduct(1L));
    }

    private Product createProduct() {


        Category category = Category.builder()
                .id(1L)
                .name("Eletrônicos")
                .description("Categoria")
                .build();

        when(categoryService.findByName(anyString()))
                .thenReturn(category);

        return Product.builder()
                .id(1L)
                .name("Notebook")
                .description("Dell")
                .price(BigDecimal.valueOf(5000))
                .quantity(10)
                .minStock(2)
                .category(category)
                .build();
    }

}