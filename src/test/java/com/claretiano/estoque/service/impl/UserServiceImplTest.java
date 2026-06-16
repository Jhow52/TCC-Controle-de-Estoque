package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.enums.Roles;
import com.claretiano.estoque.handler.IllegalOperationException;
import com.claretiano.estoque.handler.UserNotFoundException;
import com.claretiano.estoque.model.User;
import com.claretiano.estoque.repository.UserRepository;
import com.claretiano.estoque.response.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFindUserById(){

        User user = User.builder()
                .id(1L)
                .name("Jhonata")
                .email("jhow@gmail.com")
                .roles(Set.of(Roles.ROLE_USER))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Jhonata", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound(){

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()-> userService.findById(1L));
    }

    @Test
    void shouldListAllUsers(){

        User user = User.builder()
                .id(1L)
                .name("Jhonata")
                .email("jhow@gmail.com")
                .roles(Set.of(Roles.ROLE_USER))
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDTO> result = userService.listAllUsers();

        assertEquals(1, result.size());
        assertEquals("Jhonata", result.getFirst().getName());
    }

    @Test
    void shouldPromoteUserToAdmin(){
        User user = User.builder()
                .id(1L)
                .name("Jhonata")
                .roles(new HashSet<>(
                        Set.of(Roles.ROLE_USER)
                ))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDTO result = userService.promoteToAdmin(1L);

        assertTrue(user.getRoles().contains(Roles.ROLE_ADMIN));

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPromotingNonExistingUser(){

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() -> userService.promoteToAdmin(1L));
    }

    @Test
    void shouldRemoveAdminRole(){
        User admin = User.builder()
                .id(2L)
                .name("Admin")
                .email("admin2@gmail.com")
                .roles(new HashSet<>(
                        Set.of(Roles.ROLE_ADMIN)
                ))
                .build();

        User anotherAdmin = User.builder()
                .id(1L)
                .email("root@gmail.com")
                .roles(new HashSet<>(
                        Set.of(Roles.ROLE_ADMIN)
                ))
                .build();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("root@gmail.com", null));

        when(userRepository.findById(2L)).thenReturn(Optional.of(admin));

        when(userRepository.findAll()).thenReturn(List.of(admin, anotherAdmin));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.removeAdmin(2L);

        assertFalse(admin.getRoles().contains(Roles.ROLE_ADMIN));
    }

    @Test
    void shouldNotAllowRemovingOwnAdminRole(){
        User admin = User.builder()
                .id(1L)
                .email("admin@gmail.com")
                .roles(new HashSet<>(
                        Set.of(Roles.ROLE_ADMIN)
                ))
                .build();

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                "admin@gmail.com",
                                null
                        )
                );

        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));

        assertThrows(IllegalOperationException.class,() -> userService.removeAdmin(1L));
    }

    @Test
    void shouldNotAllowRemovingLastAdmin(){
        User admin = User.builder()
                .id(1L)
                .email("admin@gmail.com")
                .roles(new HashSet<>(
                        Set.of(Roles.ROLE_ADMIN)
                ))
                .build();

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                "another@gmail.com",
                                null
                        )
                );

        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(userRepository.findAll()).thenReturn(List.of(admin));
        assertThrows(IllegalOperationException.class,() -> userService.removeAdmin(1L));
    }

    @Test
    void shouldThrowExceptionWhenRemovingAdminFromNonExistingUser(){

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(
                UserNotFoundException.class,
                () -> userService.removeAdmin(1L)
        );
    }

    @Test
    void listAllUsers() {
    }

    @Test
    void findById() {
    }

    @Test
    void promoteToAdmin() {
    }

    @Test
    void removeAdmin() {
    }
}