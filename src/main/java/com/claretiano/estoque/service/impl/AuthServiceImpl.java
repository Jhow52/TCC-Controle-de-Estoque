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
import com.claretiano.estoque.service.AuthService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() ->
                new EmailNotFoundException("Email Invalido"));

        validatePassword(loginRequestDTO, user);

        return LoginResponseDTO.builder()
                .message("Login realizado com sucesso")
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @Override
    public UserResponseDTO register(UserRequestDTO userRequestDTO) {

        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Já existe um usuario com esse email");
        }

        User user = toEntity(userRequestDTO);

        User userSalvo = userRepository.save(user);
        return toResponseDTO(userSalvo);
    }

    public void validatePassword(LoginRequestDTO loginRequestDTO, User user){
        boolean password = passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword());

        if(!password){
            throw new PasswordIncorrectException("Senha Invalida");
        }
    }

    private User toEntity(UserRequestDTO userRequestDTO){
        return User.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .roles(Set.of(Roles.ROLE_USER))
                .build();
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
