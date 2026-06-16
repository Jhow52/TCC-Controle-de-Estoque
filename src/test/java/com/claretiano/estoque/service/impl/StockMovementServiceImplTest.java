package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.enums.MovementType;
import com.claretiano.estoque.handler.InventoryNotFoundException;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.model.StockMovement;
import com.claretiano.estoque.repository.ProductRepository;
import com.claretiano.estoque.repository.StockMovementRepository;
import com.claretiano.estoque.request.StockMovementRequestDTO;
import com.claretiano.estoque.response.StockMovementResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StockMovementServiceImplTest {

    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private StockMovementServiceImpl stockMovementService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldListAllStockMovements() {

        StockMovement movement = createMovement();

        when(stockMovementRepository.findAll())
                .thenReturn(List.of(movement));

        List<StockMovementResponseDTO> result =
                stockMovementService.listAllStockMovements();

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindMovementById() {

        StockMovement movement = createMovement();

        when(stockMovementRepository.findById(1L))
                .thenReturn(Optional.of(movement));

        StockMovementResponseDTO result =
                stockMovementService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldRegisterEntry() {

        Product product = createProduct();

        StockMovementRequestDTO dto =
                StockMovementRequestDTO.builder()
                        .productId(1L)
                        .quantity(5)
                        .notes("Entrada")
                        .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(stockMovementRepository.save(any(StockMovement.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StockMovementResponseDTO result =
                stockMovementService.registerEntry(dto);

        assertEquals(15, product.getQuantity());
        assertEquals(MovementType.IN, result.getMovementType());
    }

    @Test
    void shouldThrowExceptionWhenRegisteringEntryWithProductNotFound(){
        StockMovementRequestDTO dto =
                StockMovementRequestDTO.builder()
                        .productId(1L)
                        .quantity(5)
                        .build();

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                InventoryNotFoundException.class,
                () -> stockMovementService.registerEntry(dto)
        );
    }

    @Test
    void shouldRegisterExit() {

        Product product = createProduct();

        StockMovementRequestDTO dto =
                StockMovementRequestDTO.builder()
                        .productId(1L)
                        .quantity(5)
                        .notes("Saída")
                        .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(stockMovementRepository.save(any(StockMovement.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StockMovementResponseDTO result =
                stockMovementService.registerExit(dto);

        assertEquals(5, product.getQuantity());

        assertEquals(
                MovementType.OUT,
                result.getMovementType()
        );
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {

        Product product = createProduct();

        StockMovementRequestDTO dto =
                StockMovementRequestDTO.builder()
                        .productId(1L)
                        .quantity(50)
                        .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                InventoryNotFoundException.class,
                () -> stockMovementService.registerExit(dto)
        );
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundOnExit() {

        StockMovementRequestDTO dto =
                StockMovementRequestDTO.builder()
                        .productId(1L)
                        .quantity(5)
                        .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                InventoryNotFoundException.class,
                () -> stockMovementService.registerExit(dto)
        );
    }

    @Test
    void shouldFindMovementsByProductName() {

        StockMovement movement = createMovement();

        when(stockMovementRepository
                .findByProductNameContainingIgnoreCase("Notebook"))
                .thenReturn(List.of(movement));

        List<StockMovementResponseDTO> result =
                stockMovementService.findByProductName("Notebook");

        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenNoMovementsFound() {

        when(stockMovementRepository
                .findByProductNameContainingIgnoreCase("Notebook"))
                .thenReturn(Collections.emptyList());

        assertThrows(
                InventoryNotFoundException.class,
                () -> stockMovementService.findByProductName("Notebook")
        );
    }

    private Product createProduct() {
        return Product.builder()
                .id(1L)
                .name("Notebook")
                .quantity(10)
                .build();
    }

    private StockMovement createMovement() {
        return StockMovement.builder()
                .id(1L)
                .product(createProduct())
                .quantity(5)
                .type(MovementType.IN)
                .notes("Entrada")
                .movementDate(LocalDateTime.now())
                .build();
    }
}