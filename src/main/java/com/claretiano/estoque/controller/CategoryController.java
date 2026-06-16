package com.claretiano.estoque.controller;

import com.claretiano.estoque.request.CategoryRequestDTO;
import com.claretiano.estoque.response.CategoryResponseDTO;
import com.claretiano.estoque.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/category")
    public List<CategoryResponseDTO> listAllCategories(){
        return categoryService.listAllCategories();
    }

    @GetMapping(path = "/category/nome")
    public List<CategoryResponseDTO> findCategoryByName(@RequestParam String nome){
        return categoryService.findCategoryByName(nome);
    }

    @PostMapping(path = "/admin/category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryDTO){
        CategoryResponseDTO category = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping(path = "/category/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping(path = "/admin/category/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryDTO){
        CategoryResponseDTO categoryUpdated = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(categoryUpdated);
    }

    @DeleteMapping(path = "/admin/category/{id}")
    public ResponseEntity<CategoryResponseDTO> removeCategories(@PathVariable Long id){
        CategoryResponseDTO categoryDelete = categoryService.removeCategories(id);
        return ResponseEntity.ok(categoryDelete);
    }
}