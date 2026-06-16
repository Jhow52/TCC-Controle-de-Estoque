package com.claretiano.estoque.service;

import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.request.CategoryRequestDTO;
import com.claretiano.estoque.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    List<CategoryResponseDTO> listAllCategories();
    CategoryResponseDTO removeCategories(Long id);
    CategoryResponseDTO findById(Long id);
    Category findByName(String name);
    List<CategoryResponseDTO> findCategoryByName(String name);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);
}
