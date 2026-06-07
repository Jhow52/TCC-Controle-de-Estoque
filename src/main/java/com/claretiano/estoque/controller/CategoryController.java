package com.claretiano.estoque.controller;

import com.claretiano.estoque.request.CategoryRequestDTO;
import com.claretiano.estoque.request.MensagemRequestDTO;
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
    public List<CategoryResponseDTO> listarCategoria(){
        return categoryService.listarCategorias();
    }

    @PostMapping(path = "/admin/category")
    public ResponseEntity<CategoryResponseDTO> criarCategoria(@Valid @RequestBody CategoryRequestDTO categoryDTO){
        CategoryResponseDTO category = categoryService.criarCategoria(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping(path = "/category/{id}")
    public ResponseEntity<CategoryResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping(path = "/admin/category/{id}")
    public ResponseEntity<CategoryResponseDTO> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryDTO){
        CategoryResponseDTO categoryAtualizado = categoryService.atualizarCategoria(id, categoryDTO);
        return ResponseEntity.ok(categoryAtualizado);
    }

    @DeleteMapping(path = "/admin/category/{id}")
    public ResponseEntity<MensagemRequestDTO> deletarCategory(@PathVariable Long id){
        categoryService.deletarCategoria(id);
        return ResponseEntity.ok(new MensagemRequestDTO("Categoria removida com sucesso"));
    }
}