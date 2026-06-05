package com.claretiano.estoque.service;

import com.claretiano.estoque.model.User;

import java.util.List;

public interface UserService {
    User criarUsuario(User user);
    List<User> listarUsuario();
}
