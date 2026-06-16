package com.claretiano.estoque.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementRequestDTO {
    @NotNull(message = "Product id is required")
    private Long productId;
    @NotNull(message = "Quantity is required")
    @Positive(message = "The quantity must be greater than zero")
    private Integer quantity;
    private String notes;
}
