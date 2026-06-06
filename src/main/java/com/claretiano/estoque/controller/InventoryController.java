package com.claretiano.estoque.controller;

import com.claretiano.estoque.response.InventoryResponseDTO;
import com.claretiano.estoque.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponseDTO> listarInventario(){
        return inventoryService.listarInventario();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(inventoryService.buscarPorId(id));
    }

    @GetMapping("/nome")
    public List<InventoryResponseDTO> buscarPorNome(@RequestParam String nome){
        return inventoryService.buscarPorNome(nome);
    }

    @GetMapping("/categoria")
    public List<InventoryResponseDTO> buscarPorCategoria(@RequestParam String categoria){
        return inventoryService.buscarPorCategoria(categoria);
    }

    @GetMapping("/estoqueBaixo")
    public List<InventoryResponseDTO> estoqueBaixo(){
        return inventoryService.estoqueBaixo();
    }
}
