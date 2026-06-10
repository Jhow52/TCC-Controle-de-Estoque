package com.claretiano.estoque.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDTO {
    private Long id;
    private String productName;
    private Integer quantity;
    private Integer minStock;
    private String categoryName;
    private Boolean lowStock;
    private LocalDateTime date;
}
