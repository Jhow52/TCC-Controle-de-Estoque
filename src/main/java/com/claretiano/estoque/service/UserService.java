package com.claretiano.estoque.service;

import com.claretiano.estoque.model.User;
import com.claretiano.estoque.request.UserRequestDTO;
import com.claretiano.estoque.response.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> listAllUsers();
    UserResponseDTO findById(Long id);
    UserResponseDTO promoteToAdmin(Long id);
    UserResponseDTO removeAdmin(Long id);
}
