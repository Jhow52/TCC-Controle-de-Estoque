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
    public List<StockMovementResponseDTO> listAllStockMovements(){
        return stockMovementService.listAllStockMovements();
    }

    @GetMapping(path = "/movimentacao-estoque/{id}")
    public ResponseEntity<StockMovementResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(stockMovementService.findById(id));
    }

    @GetMapping(path = "/movimentacao-estoque/produto")
    public List<StockMovementResponseDTO> findByProductName(@RequestParam String productName){
        return stockMovementService.findByProductName(productName);
    }

    @PostMapping(path = "/admin/movimentacao-estoque/entrada")
    public ResponseEntity<StockMovementResponseDTO> registerEntry(@Valid @RequestBody StockMovementRequestDTO stockMovement){
        StockMovementResponseDTO stockMovementUpdate = stockMovementService.registerEntry(stockMovement);
        return ResponseEntity.ok(stockMovementUpdate);
    }

    @PostMapping(path = "/admin/movimentacao-estoque/saida")
    public ResponseEntity<StockMovementResponseDTO> registerExit(@Valid @RequestBody StockMovementRequestDTO stockMovement){
        StockMovementResponseDTO stockMovementUpdate = stockMovementService.registerExit(stockMovement);
        return ResponseEntity.ok(stockMovementUpdate);
    }
}
