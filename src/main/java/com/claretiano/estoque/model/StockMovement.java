package com.claretiano.estoque.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_stockMovement")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private LocalDateTime movementDate;
    @ManyToOne
    private User user;
    @Column(length = 255)
    private String notes;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @PrePersist
    public void prePersist(){
        movementDate = LocalDateTime.now();
    }
}
