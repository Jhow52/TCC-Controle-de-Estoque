package com.claretiano.estoque.repository;

import com.claretiano.estoque.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByNomeNormalizado(String nomeNormalizado);
}
