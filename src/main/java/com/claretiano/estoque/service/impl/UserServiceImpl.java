package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.enums.Roles;
import com.claretiano.estoque.handler.UserNotFoundException;
import com.claretiano.estoque.model.User;
import com.claretiano.estoque.repository.UserRepository;
import com.claretiano.estoque.request.ProductRequestDTO;
import com.claretiano.estoque.request.UserRequestDTO;
import com.claretiano.estoque.response.UserResponseDTO;
import com.claretiano.estoque.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(role -> role.name())
                                .toArray(String[]::new)
                )
                .build();
    }

    @Override
    public List<UserResponseDTO> listarUsuario() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO buscarPorId(Long id) {
        return userRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new UserNotFoundException("O usuario com id " + id + " não foi encontrado"));
    }

    @Override
    public UserResponseDTO promoverParaAdmim(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario com o id " + id + " não encontrado"));

        user.getRoles().add(Roles.ROLE_ADMIN);
        User userNovo = userRepository.save(user);
        return toResponseDTO(userNovo);
    }

    private UserResponseDTO toResponseDTO(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .date(user.getCreatedAt())
                .roles(user.getRoles()
                        .stream()
                        .map(Roles::name)
                        .collect(Collectors.toSet()))
                .build();
    }
}
