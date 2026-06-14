package com.claretiano.estoque.service;

import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.request.ProductRequestDTO;
import com.claretiano.estoque.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO criarProduto(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> listarProdutos();
    ProductResponseDTO atualizar(Long id, ProductRequestDTO product);
    ProductResponseDTO remover(Long id);
    ProductResponseDTO buscarProdutoPorId(Long id);
    List<ProductResponseDTO> buscarProdutoPorNome(String nome);
}
