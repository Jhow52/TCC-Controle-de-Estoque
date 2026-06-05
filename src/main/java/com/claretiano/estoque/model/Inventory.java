package com.claretiano.estoque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private InventoryType type;

    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<InventorySector> sectors;

    @PrePersist
    public void prePersist(){
        lastUpdate = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        lastUpdate = LocalDateTime.now();
    }
}
