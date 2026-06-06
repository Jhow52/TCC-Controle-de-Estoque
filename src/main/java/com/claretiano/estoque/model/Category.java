package com.claretiano.estoque.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
    private String nomeNormalizado;
}
