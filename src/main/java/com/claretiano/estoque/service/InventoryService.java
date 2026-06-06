package com.claretiano.estoque.service;

import com.claretiano.estoque.response.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryResponseDTO> listarInventario();
    List<InventoryResponseDTO> buscarPorNome(String nome);
    InventoryResponseDTO buscarPorId(Long id);
    List<InventoryResponseDTO> buscarPorCategoria(String categoria);
    List<InventoryResponseDTO> estoqueBaixo();
}
