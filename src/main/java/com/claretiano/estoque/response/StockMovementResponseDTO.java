package com.claretiano.estoque.response;

import com.claretiano.estoque.model.MovementType;
import com.claretiano.estoque.model.Product;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementResponseDTO {
    private Long id;
    private String productName;
    private Integer quantity;
    private Integer currentStock;
    private MovementType movementType;
    private LocalDateTime date;
    private String notes;
}
