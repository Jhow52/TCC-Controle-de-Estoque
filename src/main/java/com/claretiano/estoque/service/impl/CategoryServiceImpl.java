package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.CategoryAlreadyExistsException;
import com.claretiano.estoque.handler.CategoryCreateNotFoundException;
import com.claretiano.estoque.handler.CategoryInUseException;
import com.claretiano.estoque.handler.CategoryNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.repository.CategoryRepository;
import com.claretiano.estoque.request.CategoryRequestDTO;
import com.claretiano.estoque.response.CategoryResponseDTO;
import com.claretiano.estoque.service.CategoryService;
import com.claretiano.estoque.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryDTO) {
        String nameNormalized = StringUtils.normalize(categoryDTO.getName());

        Optional<Category> existingCategory = categoryRepository.findByNameNormalized(nameNormalized);

        if(existingCategory.isPresent()){
            throw new CategoryAlreadyExistsException("The category " + categoryDTO.getName() + " has already been created");
        }

        Category category = toEntity(categoryDTO);
        Category categorySaved = categoryRepository.save(category);
        return toResponseDTO(categorySaved);
    }

    @Override
    public CategoryResponseDTO findById(Long id){
        return categoryRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new CategoryCreateNotFoundException("The category with id " + id + " was not found"));
    }

    @Override
    public Category findByName(String name) {
        String nameNormalized = StringUtils.normalize(name);
        return categoryRepository.findByNameNormalized(nameNormalized).orElseThrow(() ->
                new CategoryCreateNotFoundException("The category " + name + " was not found. Please create the category before registering the product." ));
    }

    @Override
    public List<CategoryResponseDTO> findCategoryByName(String name) {
        List<Category> categoryList = categoryRepository.findAllByNameContainingIgnoreCase(name);

        if(categoryList.isEmpty()){
            throw new CategoryNotFoundException("Category " + name + " not found");
        }

        return categoryList.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));

        String nameNormalized = StringUtils.normalize(categoryDTO.getName());

        Optional<Category> categoryExisting = categoryRepository.findByNameNormalized(nameNormalized);

        if(categoryExisting.isPresent() && !categoryExisting.get().getId().equals(id)){
            throw new CategoryAlreadyExistsException("The category " + categoryDTO.getName() + " has already been created");
        }

        update(category, categoryDTO);
        Category categoryUpdated = categoryRepository.save(category);
        return toResponseDTO(categoryUpdated);
    }

    @Override
    public List<CategoryResponseDTO> listAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public CategoryResponseDTO removeCategories(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryCreateNotFoundException ("Category with id " + id + " not found"));

        if(!category.getProducts().isEmpty()){
            throw new CategoryInUseException("You cannot delete: There are products associated with this category");
        }

        categoryRepository.delete(category);
        return toResponseDTO(category);
    }

    private void update(Category category, CategoryRequestDTO categoryRequestDTO){
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        category.setNameNormalized(
                StringUtils.normalize(categoryRequestDTO.getName())
        );
    }

    private Category toEntity(CategoryRequestDTO categoryRequestDTO){
        String nameNormalized = StringUtils.normalize(categoryRequestDTO.getName());
        return Category.builder()
                .name(categoryRequestDTO.getName())
                .description(categoryRequestDTO.getDescription())
                .nameNormalized(nameNormalized)
                .build();
    }

    private CategoryResponseDTO toResponseDTO(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
