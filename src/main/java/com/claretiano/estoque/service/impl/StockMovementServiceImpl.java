package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.InventoryNotFound;
import com.claretiano.estoque.model.MovementType;
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
    public List<StockMovementResponseDTO> listarStockMovements() {
        return stockMovementRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public StockMovementResponseDTO buscarPorId(Long id) {
        return stockMovementRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new InventoryNotFound("Movimentação com id " + id + " não foi encontrado"));
    }

    @Override
    public StockMovementResponseDTO entrada(StockMovementRequestDTO stockMovementRequestDTO) {
        Product product = productRepository.findById(stockMovementRequestDTO.getProductId()).orElseThrow(() ->
                new InventoryNotFound("O produto com o id " + stockMovementRequestDTO.getProductId() + " não foi encontrado"));

        product.setQuantity(product.getQuantity() + stockMovementRequestDTO.getQuantity());

        productRepository.save(product);

        StockMovement movement = StockMovement.builder()
                .product(product)
                .quantity(stockMovementRequestDTO.getQuantity())
                .notes(stockMovementRequestDTO.getNotes())
                .type(MovementType.IN)
                .build();

        stockMovementRepository.save(movement);
        return toResponseDTO(movement);
    }

    @Override
    public StockMovementResponseDTO saida(StockMovementRequestDTO stockMovementRequestDTO) {
        Product product = productRepository.findById(stockMovementRequestDTO.getProductId()).orElseThrow(() ->
                new InventoryNotFound("O produto com o id " + stockMovementRequestDTO.getProductId() + " não foi encontrado"));

        if(product.getQuantity() < stockMovementRequestDTO.getQuantity()) {
            throw new InventoryNotFound("Estoque insuficiente para realizar a saída");
        }

        product.setQuantity(product.getQuantity() - stockMovementRequestDTO.getQuantity());

        productRepository.save(product);

        StockMovement movement = StockMovement.builder()
                .product(product)
                .quantity(stockMovementRequestDTO.getQuantity())
                .notes(stockMovementRequestDTO.getNotes())
                .type(MovementType.OUT)
                .build();

        StockMovement movementSaved = stockMovementRepository.save(movement);
        return toResponseDTO(movement);
    }

    @Override
    public List<StockMovementResponseDTO> buscarPorProduto(String productName) {
        List<StockMovement> movements = stockMovementRepository.findByProduct_NameContainingIgnoreCase(productName);

        if(movements.isEmpty()){
            throw new InventoryNotFound("Nenhuma movimentação encontrada para o produto " + productName);
        }

        return movements.stream()
                .map(this::toResponseDTO)
                .toList();
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
