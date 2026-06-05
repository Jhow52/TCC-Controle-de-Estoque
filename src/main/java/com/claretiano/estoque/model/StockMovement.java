package com.claretiano.estoque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_stockMovement")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product produto;
    @Column(nullable = false)
    private Integer quantity;
    private LocalDateTime movementDate;
    @ManyToOne
    private User user;
    @Column(length = 255)
    private String notes;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    //Atualização após a retirada do item(s)
    @PreUpdate
    public void preUpdate(){
        movementDate = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist(){
        movementDate = LocalDateTime.now();
    }
}
