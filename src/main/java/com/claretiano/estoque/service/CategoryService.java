package com.claretiano.estoque.service;

import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.request.CategoryRequestDTO;
import com.claretiano.estoque.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO criarCategoria(CategoryRequestDTO categoryRequestDTO);
    List<CategoryResponseDTO> listarCategorias();
    Category deletarCategoria(Long id);
    CategoryResponseDTO findById(Long id);
    Category buscarPorNome(String nome);
    List<CategoryResponseDTO> buscarCategoriaPorNome(String nome);
    CategoryResponseDTO atualizarCategoria(Long id, CategoryRequestDTO categoryRequestDTO);
}
