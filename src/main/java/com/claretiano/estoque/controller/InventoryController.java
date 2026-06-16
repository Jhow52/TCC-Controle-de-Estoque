package com.claretiano.estoque.controller;

import com.claretiano.estoque.response.InventoryResponseDTO;
import com.claretiano.estoque.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping(path = "/inventario")
    public List<InventoryResponseDTO> listAllInventory(){
        return inventoryService.listAllInventory();
    }

    @GetMapping(path = "/inventario/{id}")
    public ResponseEntity<InventoryResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(inventoryService.findById(id));
    }

    @GetMapping(path = "/inventario/nome")
    public List<InventoryResponseDTO> findByName(@RequestParam String name){
        return inventoryService.findByName(name);
    }

    @GetMapping(path = "/inventario/categoria")
    public List<InventoryResponseDTO> findByCategory(@RequestParam String category){
        return inventoryService.findByCategory(category);
    }

    @GetMapping(path = "/inventario/estoqueBaixo")
    public List<InventoryResponseDTO> findLowStockProducts(){
        return inventoryService.findLowStockProducts();
    }
}
