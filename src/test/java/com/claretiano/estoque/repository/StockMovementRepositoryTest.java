package com.claretiano.estoque.repository;

import com.claretiano.estoque.enums.MovementType;
import com.claretiano.estoque.enums.Roles;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.model.StockMovement;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class StockMovementRepositoryTest {

    @Autowired
    StockMovementRepository stockMovementRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldFindStockMovementByProductName(){
        createStockMovement();

        List<StockMovement> result = stockMovementRepository.findByProductNameContainingIgnoreCase("Note");

        assertFalse(result.isEmpty());

        assertEquals("Notebook", result.getFirst().getProduct().getName());
    }

    @Test
    void shouldFindStockMovementIgnoringCase(){
        createStockMovement();

        List<StockMovement> result = stockMovementRepository.findByProductNameContainingIgnoreCase("NOTEBOOK");

        assertFalse(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenProductNotExists(){

        List<StockMovement> result = stockMovementRepository.findByProductNameContainingIgnoreCase("Mouse");

        assertTrue(result.isEmpty());
    }

    @Test
    void findByProductNameContainingIgnoreCase() {
    }

    private StockMovement createStockMovement(){
        Product product = createProduct();

        User user = createUser();

        StockMovement movement = StockMovement.builder()
                .product(product)
                .user(user)
                .quantity(5)
                .notes("Entrada de estoque")
                .type(MovementType.IN)
                .build();

        entityManager.persist(movement);
        entityManager.flush();

        return movement;
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

    private User createUser() {
        User user = User.builder()
                .name("Jhonata")
                .email("jhow@gmail.com")
                .password("123456")
                .roles(new HashSet<>(Set.of(Roles.ROLE_USER)))
                .build();
        this.entityManager.persist(user);
        entityManager.flush();
        return user;
    }
}