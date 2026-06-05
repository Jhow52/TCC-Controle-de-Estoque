package com.claretiano.estoque.controller;

import com.claretiano.estoque.model.User;
import com.claretiano.estoque.service.UserService;
import com.claretiano.estoque.request.UserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> criarUsuario(@RequestBody UserRequestDTO userDTO){
        try {
            User user = new User();
            user.setNome(userDTO.getNome());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setRoles(userDTO.getRoles()); //Tirar depois por conta que o user não pode definir o seu role no sistema

            User userSalvo = userService.criarUsuario(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userSalvo);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public List<User> listarUsuario(){
        return userService.listarUsuario();
    }
}
