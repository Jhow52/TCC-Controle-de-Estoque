package com.claretiano.estoque.repository;

import com.claretiano.estoque.model.Category;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldFindCategoryByNameNormalized(){
        createCategory();

        Optional<Category> result = categoryRepository.findByNameNormalized("eletronicos");

        assertTrue(result.isPresent());

        assertEquals("Eletrônicos", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenCategoryDoesNotExist(){

        Optional<Category> result = categoryRepository.findByNameNormalized("informatica");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindCategoryByPartialName(){
        createCategory();

        List<Category> result = categoryRepository.findAllByNameContainingIgnoreCase("ele");

        assertFalse(result.isEmpty());

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindCategoryIgnoringCase(){
        createCategory();

        List<Category> result = categoryRepository.findAllByNameContainingIgnoreCase("ELETRÔNICOS");

        assertFalse(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenCategoryNotExists(){

        List<Category> result = categoryRepository.findAllByNameContainingIgnoreCase("roupas");

        assertTrue(result.isEmpty());
    }

    private Category createCategory() {
        Category category = Category.builder()
                .name("Eletrônicos")
                .description("Categoria de testes")
                .nameNormalized("eletronicos")
                .build();

        entityManager.persist(category);
        entityManager.flush();

        return category;
    }
}