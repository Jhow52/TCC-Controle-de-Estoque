package com.claretiano.estoque.controller;

import com.claretiano.estoque.request.LoginRequestDTO;
import com.claretiano.estoque.request.UserRequestDTO;
import com.claretiano.estoque.response.LoginResponseDTO;
import com.claretiano.estoque.response.UserResponseDTO;
import com.claretiano.estoque.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userDTO){
        UserResponseDTO user = authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO){
        LoginResponseDTO userLogin = authService.login(loginDTO);
        return ResponseEntity.ok(userLogin);
    }
}
