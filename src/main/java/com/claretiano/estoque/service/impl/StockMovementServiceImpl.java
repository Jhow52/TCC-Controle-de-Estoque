package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.InventoryNotFoundException;
import com.claretiano.estoque.enums.MovementType;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.model.StockMovement;
import com.claretiano.estoque.repository.ProductRepository;
import com.claretiano.estoque.repository.StockMovementRepository;
import com.claretiano.estoque.request.StockMovementRequestDTO;
import com.claretiano.estoque.response.StockMovementResponseDTO;
import com.claretiano.estoque.service.StockMovementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    public StockMovementServiceImpl(StockMovementRepository stockMovementRepository, ProductRepository productRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<StockMovementResponseDTO> listAllStockMovements() {
        return stockMovementRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public StockMovementResponseDTO findById(Long id) {
        return stockMovementRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new InventoryNotFoundException("Movement with id " + id + " was not found"));
    }

    @Override
    public StockMovementResponseDTO registerEntry(StockMovementRequestDTO stockMovementRequestDTO) {
        Product product = productRepository.findById(stockMovementRequestDTO.getProductId()).orElseThrow(() ->
                new InventoryNotFoundException("The product with id " + stockMovementRequestDTO.getProductId() + " was not found"));

        product.setQuantity(product.getQuantity() + stockMovementRequestDTO.getQuantity());

        productRepository.save(product);

        StockMovement movement = toEntity(stockMovementRequestDTO, product, MovementType.IN);
        stockMovementRepository.save(movement);
        return toResponseDTO(movement);
    }

    @Override
    public StockMovementResponseDTO registerExit(StockMovementRequestDTO stockMovementRequestDTO) {
        Product product = productRepository.findById(stockMovementRequestDTO.getProductId()).orElseThrow(() ->
                new InventoryNotFoundException("The product with id " + stockMovementRequestDTO.getProductId() + " was not found"));

        if(product.getQuantity() < stockMovementRequestDTO.getQuantity()) {
            throw new InventoryNotFoundException("Insufficient stock to fulfill the order");
        }

        product.setQuantity(product.getQuantity() - stockMovementRequestDTO.getQuantity());

        productRepository.save(product);

        StockMovement movement = toEntity(stockMovementRequestDTO, product, MovementType.OUT);
        StockMovement movementSaved = stockMovementRepository.save(movement);
        return toResponseDTO(movementSaved);
    }

    @Override
    public List<StockMovementResponseDTO> findByProductName(String productName) {
        List<StockMovement> movements = stockMovementRepository.findByProductNameContainingIgnoreCase(productName);

        if(movements.isEmpty()){
            throw new InventoryNotFoundException("No movements found for the product: " + productName);
        }

        return movements.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private StockMovement toEntity(StockMovementRequestDTO stockMovementRequestDTO, Product product, MovementType type){
        return StockMovement.builder()
                .product(product)
                .quantity(stockMovementRequestDTO.getQuantity())
                .notes(stockMovementRequestDTO.getNotes())
                .type(type)
                .build();
    }

    private StockMovementResponseDTO toResponseDTO(StockMovement stockMovement) {
        return StockMovementResponseDTO.builder()
                .id(stockMovement.getId())
                .productName(stockMovement.getProduct().getName())
                .quantity(stockMovement.getQuantity())
                .movementType(stockMovement.getType())
                .currentStock(stockMovement.getProduct().getQuantity())
                .date(stockMovement.getMovementDate())
                .notes(stockMovement.getNotes())
                .build();
    }
}
