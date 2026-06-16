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
    public List<ProductResponseDTO> listAllProducts() {
        return productService.listAllProducts();
    }

    @GetMapping(path = "/produto/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping(path = "/produto/nome")
    public List<ProductResponseDTO> findByName(@RequestParam String name){
        return productService.findByName(name);
    }

    @PostMapping(path = "/admin/produto")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productDTO) {
        ProductResponseDTO product = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping(path = "/admin/produto/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO product) {
        ProductResponseDTO productUpdated = productService.updateProduct(id, product);
        return ResponseEntity.ok(productUpdated);
    }

    @DeleteMapping(path = "/admin/produto/{id}")
    public ResponseEntity<ProductResponseDTO> removeProduct(@PathVariable Long id) {
        ProductResponseDTO product = productService.removeProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
