package com.claretiano.estoque.controller;

import com.claretiano.estoque.request.StockMovementRequestDTO;
import com.claretiano.estoque.response.StockMovementResponseDTO;
import com.claretiano.estoque.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock-movement")
public class StockMovementController {

    private StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping
    public List<StockMovementResponseDTO> listarStockMovement(){
        return stockMovementService.listarStockMovements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponseDTO> listaPorId(@PathVariable Long id){
        return ResponseEntity.ok(stockMovementService.buscarPorId(id));
    }

    @GetMapping("/produto")
    public List<StockMovementResponseDTO> buscarPorProduto(@RequestParam String productName){
        return stockMovementService.buscarPorProduto(productName);
    }

    @PostMapping("/entrada")
    public ResponseEntity<StockMovementResponseDTO> entradaStockMovement(@Valid @RequestBody StockMovementRequestDTO stockMovement){
        StockMovementResponseDTO stockMovementUpdate = stockMovementService.entrada(stockMovement);
        return ResponseEntity.ok(stockMovementUpdate);
    }

    @PostMapping("/saida")
    public ResponseEntity<StockMovementResponseDTO> saidaStockMovement(@Valid @RequestBody StockMovementRequestDTO stockMovement){
        StockMovementResponseDTO stockMovementeUpdadte = stockMovementService.saida(stockMovement);
        return ResponseEntity.ok(stockMovementeUpdadte);
    }
}
