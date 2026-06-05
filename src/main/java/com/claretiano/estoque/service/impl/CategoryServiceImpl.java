package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.CategoryCreateNotFoundException;
import com.claretiano.estoque.handler.CategoryEmUsoException;
import com.claretiano.estoque.handler.CategoryNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.repository.CategoryRepository;
import com.claretiano.estoque.request.CategoryRequestDTO;
import com.claretiano.estoque.response.CategoryResponseDTO;
import com.claretiano.estoque.service.CategoryService;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO criarCategoria(CategoryRequestDTO categoryDTO) {

        String nomeNormalizado = normalizar(categoryDTO.getName());

        Category category = Category.builder()
                .name(categoryDTO.getName().trim())
                .description(categoryDTO.getDescription().trim())
                .nomeNormalizado(nomeNormalizado)
                .build();

        Category categorySalvo = categoryRepository.save(category);
        return toResponseDTO(categorySalvo);
    }

    @Override
    public CategoryResponseDTO findById(Long id){
        CategoryResponseDTO category = categoryRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new CategoryCreateNotFoundException("A categoria com o id " + id + " não foi encontrada"));
        return category;
    }

    @Override
    public Category buscarPorNome(String nome) {
        String nomeNormalizado = normalizar(nome);
        return categoryRepository.findByNomeNormalizado(nomeNormalizado).orElseThrow(() -> new CategoryCreateNotFoundException("Categoria" + nome + " não encontrada. Crie a categoria antes de cadastrar um produto." ));
    }

    @Override
    public CategoryResponseDTO atualizarCategoria(Long id, CategoryRequestDTO categoryDTO) {
        Category categoryExists = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        categoryExists.setName(categoryDTO.getName());
        categoryExists.setDescription(categoryDTO.getDescription());

        Category categoryAtualizado = categoryRepository.save(categoryExists);
        return toResponseDTO(categoryAtualizado);
    }

    @Override
    public List<CategoryResponseDTO> listarCategorias() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public Category deletarCategoria(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryCreateNotFoundException ("Categoria com id: " + id + " não encontrado"));

        if(!category.getProducts().isEmpty()){
            throw new CategoryEmUsoException("Não é possivel deletar: há produtos associados a está categoria");
        }

        categoryRepository.delete(category);
        return category;
    }

    private CategoryResponseDTO toResponseDTO(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public String normalizar(String texto) {
        if (texto == null) return null;

        String textoSemAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return textoSemAcento.toLowerCase().trim();
    }
}
