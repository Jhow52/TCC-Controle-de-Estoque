package com.claretiano.estoque.controller;

import com.claretiano.estoque.model.User;
import com.claretiano.estoque.response.UserResponseDTO;
import com.claretiano.estoque.service.UserService;
import com.claretiano.estoque.request.UserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin/user")
    public List<UserResponseDTO> listarUsuarios(){
        return userService.listarUsuario();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin/user/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(userService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/promover/{id}")
    public ResponseEntity<UserResponseDTO> promoverParaAdmin(@PathVariable Long id){
        return ResponseEntity.ok(userService.promoverParaAdmim(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/remover/{id}")
    public ResponseEntity<UserResponseDTO> removerAdmin(@PathVariable Long id){
        return ResponseEntity.ok(userService.removerAdmin(id));
    }
}
