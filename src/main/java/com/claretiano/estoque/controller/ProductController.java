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
@RequestMapping("/v1")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/produto")
    public List<ProductResponseDTO> listarProdutos() {
        return productService.listarProdutos();
    }

    @GetMapping(path = "/produto/{id}")
    public ResponseEntity<ProductResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(productService.buscarProdutoPorId(id));
    }

    @PostMapping(path = "/admin/produto")
    public ResponseEntity<ProductResponseDTO> criarProduto(@Valid @RequestBody ProductRequestDTO productDTO) {
        ProductResponseDTO product = productService.criarProduto(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping(path = "/admin/produto/{id}")
    public ResponseEntity<ProductResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO product) {
        ProductResponseDTO productAtualizado = productService.atualizar(id, product);
        return ResponseEntity.ok(productAtualizado);
    }

    @DeleteMapping(path = "/admin/produto/{id}")
    public ResponseEntity<ProductResponseDTO> deletarProduto(@PathVariable Long id) {
        ProductResponseDTO product = productService.remover(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
