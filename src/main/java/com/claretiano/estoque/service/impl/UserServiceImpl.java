package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.enums.Roles;

import com.claretiano.estoque.handler.IllegalOperationException;
import com.claretiano.estoque.handler.UserNotFoundException;
import com.claretiano.estoque.model.User;
import com.claretiano.estoque.repository.UserRepository;
import com.claretiano.estoque.response.UserResponseDTO;
import com.claretiano.estoque.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

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
    public List<UserResponseDTO> listAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new UserNotFoundException("The user with id " + id + " was not found"));
    }

    @Override
    public UserResponseDTO promoteToAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("The user with id " + id + " not found"));

        user.getRoles().add(Roles.ROLE_ADMIN);
        User savedUser = userRepository.save(user);
        return toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO removeAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("The user with id " + id + " not found"));

        String emailLogged = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        if(user.getEmail().equals(emailLogged)){
            throw new IllegalOperationException(
                    "You cannot remove your own administrator profile");
        }

        long totalAdmin = userRepository.findAll()
                .stream()
                .filter(u -> u.getRoles().contains(Roles.ROLE_ADMIN))
                .count();

        if(totalAdmin == 1 && user.getRoles().contains(Roles.ROLE_ADMIN)){
            throw new IllegalOperationException(
                    "It's not possible to remove the last system administrator");
        }

        user.getRoles().remove(Roles.ROLE_ADMIN);
        User useNovo = userRepository.save(user);
        return toResponseDTO(useNovo);
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
