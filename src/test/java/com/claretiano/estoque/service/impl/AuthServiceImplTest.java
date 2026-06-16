package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.enums.Roles;
import com.claretiano.estoque.handler.EmailAlreadyExistsException;
import com.claretiano.estoque.handler.EmailNotFoundException;
import com.claretiano.estoque.handler.PasswordIncorrectException;
import com.claretiano.estoque.model.User;
import com.claretiano.estoque.repository.UserRepository;
import com.claretiano.estoque.request.LoginRequestDTO;
import com.claretiano.estoque.request.UserRequestDTO;
import com.claretiano.estoque.response.LoginResponseDTO;
import com.claretiano.estoque.response.UserResponseDTO;
import com.claretiano.estoque.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequestDTO dto = LoginRequestDTO.builder()
                .email("jhow@gmail.com")
                .password("123456")
                .build();

        User user = createUser();

        when(userRepository.findByEmail("jhow@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "123456",
                "senhaCriptografada"))
                .thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("token-jwt");

        LoginResponseDTO result =
                authService.login(dto);

        assertEquals(
                "Login successfully",
                result.getMessage()
        );

        assertEquals(
                "token-jwt",
                result.getToken()
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailNotFound() {

        LoginRequestDTO dto = LoginRequestDTO.builder()
                .email("naoexiste@gmail.com")
                .password("123456")
                .build();

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(
                EmailNotFoundException.class,
                () -> authService.login(dto)
        );
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {

        LoginRequestDTO dto = LoginRequestDTO.builder()
                .email("jhow@gmail.com")
                .password("errada")
                .build();

        User user = createUser();

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "errada",
                "senhaCriptografada"))
                .thenReturn(false);

        assertThrows(
                PasswordIncorrectException.class,
                () -> authService.login(dto)
        );
    }

    @Test
    void shouldRegisterUserSuccessfully() {

        UserRequestDTO dto = UserRequestDTO.builder()
                .name("Jhonata")
                .email("jhow@gmail.com")
                .password("123456")
                .build();

        User user = createUser();

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("senhaCriptografada");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponseDTO result =
                authService.register(dto);

        assertEquals(
                "Jhonata",
                result.getName()
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        UserRequestDTO dto = UserRequestDTO.builder()
                .email("jhow@gmail.com")
                .build();

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(true);

        assertThrows(
                EmailAlreadyExistsException.class,
                () -> authService.register(dto)
        );
    }

    @Test
    void shouldValidatePasswordSuccessfully() {

        LoginRequestDTO dto = LoginRequestDTO.builder()
                .password("123456")
                .build();

        User user = createUser();

        when(passwordEncoder.matches(
                "123456",
                "senhaCriptografada"))
                .thenReturn(true);

        assertDoesNotThrow(
                () -> authService.validatePassword(dto, user)
        );
    }

    @Test
    void shouldThrowExceptionWhenPasswordValidationFails() {

        LoginRequestDTO dto = LoginRequestDTO.builder()
                .password("errada")
                .build();

        User user = createUser();

        when(passwordEncoder.matches(
                "errada",
                "senhaCriptografada"))
                .thenReturn(false);

        assertThrows(
                PasswordIncorrectException.class,
                () -> authService.validatePassword(dto, user)
        );
    }

    private User createUser() {
        return User.builder()
                .id(1L)
                .name("Jhonata")
                .email("jhow@gmail.com")
                .password("senhaCriptografada")
                .roles(Set.of(Roles.ROLE_USER))
                .build();
    }

}