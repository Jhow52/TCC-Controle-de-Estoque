package com.claretiano.estoque.repository;

import com.claretiano.estoque.enums.Roles;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldFindProductsByName() {
        createProduct();

        List<Product> result = productRepository.findAllByNameContainingIgnoreCase("note");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenProductDoesNotExist(){
        List<Product> result = productRepository.findAllByNameContainingIgnoreCase("mouse");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindProductIgnoringCase(){
        createProduct();

        List<Product> result = productRepository.findAllByNameContainingIgnoreCase("NOTEBOOK");

        assertFalse(result.isEmpty());
    }

    private Product createProduct() {
        Category category = createCategory();

        Product product = Product.builder()
                .name("Notebook")
                .description("Notebook Gamer")
                .price(BigDecimal.valueOf(5000))
                .quantity(10)
                .minStock(2)
                .category(category)
                .nameNormalized("notebook")
                .build();
        this.entityManager.persist(product);
        entityManager.flush();
        return product;
    }

    private Category createCategory(){
        Category category = Category.builder()
                .name("Eletronicos")
                .description("Categoria teste")
                .build();

        entityManager.persist(category);
        return category;
    }
}