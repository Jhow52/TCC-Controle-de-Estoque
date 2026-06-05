package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.UserCreateNotFoundException;
import com.claretiano.estoque.model.User;
import com.claretiano.estoque.repository.UserRepository;
import com.claretiano.estoque.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User criarUsuario(User user) {
        if(user.getNome() == null || user.getNome().isEmpty()){
            throw new UserCreateNotFoundException("O nome do usuário não pode ser nulo ou vazio");
        }

        if(user.getEmail() == null || user.getEmail().isEmpty()){
            throw new UserCreateNotFoundException("O email do usuário não pode ser nulo ou vazio");
        }

        if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new UserCreateNotFoundException("A senha não pode ser nula ou vazia");
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> listarUsuario() {
        return userRepository.findAll();
    }
}
