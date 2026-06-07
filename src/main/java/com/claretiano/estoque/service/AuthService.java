package com.claretiano.estoque.service;

import com.claretiano.estoque.request.LoginRequestDTO;
import com.claretiano.estoque.request.UserRequestDTO;
import com.claretiano.estoque.response.LoginResponseDTO;
import com.claretiano.estoque.response.UserResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    UserResponseDTO register(UserRequestDTO userRequestDTO);
}
