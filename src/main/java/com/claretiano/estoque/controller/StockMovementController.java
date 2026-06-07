package com.claretiano.estoque.controller;

import com.claretiano.estoque.request.StockMovementRequestDTO;
import com.claretiano.estoque.response.StockMovementResponseDTO;
import com.claretiano.estoque.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class StockMovementController {

    private StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping(path = "movimentacao-estoque")
    public List<StockMovementResponseDTO> listarStockMovement(){
        return stockMovementService.listarStockMovements();
    }

    @GetMapping(path = "/movimentacao-estoque/{id}")
    public ResponseEntity<StockMovementResponseDTO> listaPorId(@PathVariable Long id){
        return ResponseEntity.ok(stockMovementService.buscarPorId(id));
    }

    @GetMapping(path = "/movimentacao-estoque/produto")
    public List<StockMovementResponseDTO> buscarPorProduto(@RequestParam String productName){
        return stockMovementService.buscarPorProduto(productName);
    }

    @PostMapping(path = "/admin/movimentacao-estoque/entrada")
    public ResponseEntity<StockMovementResponseDTO> entradaStockMovement(@Valid @RequestBody StockMovementRequestDTO stockMovement){
        StockMovementResponseDTO stockMovementUpdate = stockMovementService.entrada(stockMovement);
        return ResponseEntity.ok(stockMovementUpdate);
    }

    @PostMapping(path = "/admin/movimentacao-estoque/saida")
    public ResponseEntity<StockMovementResponseDTO> saidaStockMovement(@Valid @RequestBody StockMovementRequestDTO stockMovement){
        StockMovementResponseDTO stockMovementUpdate = stockMovementService.saida(stockMovement);
        return ResponseEntity.ok(stockMovementUpdate);
    }
}
