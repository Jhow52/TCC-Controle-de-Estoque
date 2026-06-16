package com.claretiano.estoque.service;

import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.request.ProductRequestDTO;
import com.claretiano.estoque.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> listAllProducts();
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO product);
    ProductResponseDTO removeProduct(Long id);
    ProductResponseDTO findProductById(Long id);
    List<ProductResponseDTO> findByName(String name);
}
