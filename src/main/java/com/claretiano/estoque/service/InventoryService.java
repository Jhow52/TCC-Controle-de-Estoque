package com.claretiano.estoque.service;

import com.claretiano.estoque.response.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryResponseDTO> listAllInventory();
    List<InventoryResponseDTO> findByName(String name);
    InventoryResponseDTO findById(Long id);
    List<InventoryResponseDTO> findByCategory(String category);
    List<InventoryResponseDTO> findLowStockProducts();
}
