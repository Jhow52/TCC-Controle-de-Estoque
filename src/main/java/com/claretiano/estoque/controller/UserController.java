package com.claretiano.estoque.controller;

import com.claretiano.estoque.model.User;
import com.claretiano.estoque.response.UserResponseDTO;
import com.claretiano.estoque.service.UserService;
import com.claretiano.estoque.request.UserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user")
    public List<UserResponseDTO> listarUsuarios(){
        return userService.listarUsuario();
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(userService.buscarPorId(id));
    }
}
