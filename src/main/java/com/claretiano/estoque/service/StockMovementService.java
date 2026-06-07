package com.claretiano.estoque.service;

import com.claretiano.estoque.request.StockMovementRequestDTO;
import com.claretiano.estoque.response.StockMovementResponseDTO;

import java.util.List;

public interface StockMovementService {
    List<StockMovementResponseDTO> listarStockMovements();
    StockMovementResponseDTO buscarPorId(Long id);
    StockMovementResponseDTO entrada(StockMovementRequestDTO stockMovementRequestDTO);
    StockMovementResponseDTO saida(StockMovementRequestDTO stockMovementRequestDTO);
    List<StockMovementResponseDTO> buscarPorProduto(String productName);
}
