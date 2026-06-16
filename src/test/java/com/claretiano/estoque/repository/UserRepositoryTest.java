package com.claretiano.estoque.repository;

import com.claretiano.estoque.enums.Roles;
import com.claretiano.estoque.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldFindUserByEmailWhenExists() {
        createUser();

        Optional<User> result = userRepository.findByEmail("jhow@gmail.com");

        assertTrue(result.isPresent());
        assertEquals("jhow@gmail.com", result.get().getEmail());
    }

    @Test
    void shouldReturnEmptyWhenEmailNotExists() {
        Optional<User> result = userRepository.findByEmail("notexists@gmail.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        createUser();

        boolean result = userRepository.existsByEmail("jhow@gmail.com");

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenEmailNotExists() {
        boolean result = userRepository.existsByEmail("notexists@gmail.com");

        assertFalse(result);
    }

    @Test
    void shouldFindUserByNameWhenExists() {
        createUser();

        Optional<User> result = userRepository.findByName("Jhonata");

        assertTrue(result.isPresent());
        assertEquals("Jhonata", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenNameNotExists() {
        Optional<User> result = userRepository.findByName("NotExists");

        assertTrue(result.isEmpty());
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