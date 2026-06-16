package com.claretiano.estoque.service;

import com.claretiano.estoque.request.StockMovementRequestDTO;
import com.claretiano.estoque.response.StockMovementResponseDTO;

import java.util.List;

public interface StockMovementService {
    List<StockMovementResponseDTO> listAllStockMovements();
    StockMovementResponseDTO findById(Long id);
    StockMovementResponseDTO registerEntry(StockMovementRequestDTO stockMovementRequestDTO);
    StockMovementResponseDTO registerExit(StockMovementRequestDTO stockMovementRequestDTO);
    List<StockMovementResponseDTO> findByProductName(String productName);
}
