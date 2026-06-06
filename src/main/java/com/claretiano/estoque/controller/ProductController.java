package com.claretiano.estoque.controller;

import com.claretiano.estoque.response.ProductResponseDTO;
import com.claretiano.estoque.service.ProductService;
import com.claretiano.estoque.request.ProductRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDTO> listarProdutos() {
        return productService.listarProdutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(productService.buscarProdutoPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> criarProduto(@Valid @RequestBody ProductRequestDTO productDTO) {
        ProductResponseDTO product = productService.criarProduto(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO product) {
        ProductResponseDTO productAtualizado = productService.atualizar(id, product);
        return ResponseEntity.ok(productAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> deletarProduto(@PathVariable Long id) {
        ProductResponseDTO product = productService.buscarProdutoPorId(id);
        productService.remover(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
