package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.CategoryAlreadyExistsException;
import com.claretiano.estoque.handler.CategoryCreateNotFoundException;
import com.claretiano.estoque.handler.CategoryInUseException;
import com.claretiano.estoque.handler.CategoryNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.repository.CategoryRepository;
import com.claretiano.estoque.request.CategoryRequestDTO;
import com.claretiano.estoque.response.CategoryResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateCategory(){
        CategoryRequestDTO dto = CategoryRequestDTO.builder()
                .name("Eletrônicos")
                .description("Categoria teste")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Eletrônicos")
                .description("Categoria teste")
                .nameNormalized("eletronicos")
                .build();

        when(categoryRepository.findByNameNormalized(anyString())).thenReturn(Optional.empty());

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO result = categoryService.createCategory(dto);

        assertEquals("Eletrônicos", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExists(){
        CategoryRequestDTO dto =
                CategoryRequestDTO.builder()
                        .name("Eletrônicos")
                        .description("Categoria teste")
                        .build();

        Category category = Category.builder()
                .name("Eletrônicos")
                .build();

        when(categoryRepository.findByNameNormalized(anyString())).thenReturn(Optional.of(category));

        assertThrows(CategoryAlreadyExistsException.class,() -> categoryService.createCategory(dto));
    }

    @Test
    void shouldFindCategoryById(){
        Category category = Category.builder()
                .id(1L)
                .name("Eletrônicos")
                .description("Teste")
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponseDTO result = categoryService.findById(1L);

        assertEquals("Eletrônicos", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound(){

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryCreateNotFoundException.class,() -> categoryService.findById(1L));
    }

    @Test
    void shouldFindCategoryByName(){
        Category category = Category.builder()
                .name("Eletrônicos")
                .build();

        when(categoryRepository.findByNameNormalized(anyString())).thenReturn(Optional.of(category));

        Category result = categoryService.findByName("Eletrônicos");

        assertEquals("Eletrônicos", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenCategoryNameNotFound(){

        when(categoryRepository.findByNameNormalized(anyString())).thenReturn(Optional.empty());

        assertThrows(CategoryCreateNotFoundException.class,() -> categoryService.findByName("Teste"));
    }

    @Test
    void shouldListAllCategories(){
        Category category = Category.builder()
                .id(1L)
                .name("Eletrônicos")
                .build();

        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryResponseDTO> result = categoryService.listAllCategories();

        assertEquals(1, result.size());
    }

    @Test
    void shouldRemoveCategory(){
        Category category = Category.builder()
                .id(1L)
                .products(Collections.emptyList())
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.removeCategories(1L);

        verify(categoryRepository).delete(category);
    }

    @Test
    void shouldThrowExceptionWhenCategoryHasProducts(){
        Product product = Product.builder()
                .id(1L)
                .name("Notebook")
                .build();

        Category category = Category.builder()
                .products(List.of(product))
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        assertThrows(CategoryInUseException.class,() -> categoryService.removeCategories(1L));
    }

    @Test
    void shouldUpdateCategory(){
        CategoryRequestDTO dto =
                CategoryRequestDTO.builder()
                        .name("Hardware")
                        .description("Hardware atualizado")
                        .build();

        Category category = Category.builder()
                .id(1L)
                .name("Eletrônicos")
                .description("Descrição antiga")
                .build();

        Category categoryUpdated = Category.builder()
                .id(1L)
                .name("Hardware")
                .description("Hardware atualizado")
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.findByNameNormalized(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryUpdated);

        CategoryResponseDTO result = categoryService.updateCategory(1L, dto);

        assertEquals("Hardware", result.getName());
        assertEquals("Hardware atualizado", result.getDescription());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFoundToUpdate() {

        CategoryRequestDTO dto =
                CategoryRequestDTO.builder()
                        .name("Hardware")
                        .description("Teste")
                        .build();

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.updateCategory(1L, dto)
        );
    }

    @Test
    void shouldThrowExceptionWhenCategoryNameAlreadyExists(){
        CategoryRequestDTO dto =
                CategoryRequestDTO.builder()
                        .name("Hardware")
                        .description("Teste")
                        .build();

        Category category = Category.builder()
                .id(1L)
                .name("Eletrônicos")
                .build();

        Category existingCategory = Category.builder()
                .id(2L)
                .name("Hardware")
                .build();

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(categoryRepository.findByNameNormalized(anyString()))
                .thenReturn(Optional.of(existingCategory));

        assertThrows(
                CategoryAlreadyExistsException.class,
                () -> categoryService.updateCategory(1L, dto)
        );
    }
}