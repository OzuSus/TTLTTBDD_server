package com.TTLTTBDD.server.services;

import com.TTLTTBDD.server.models.dto.UserDTO;
import com.TTLTTBDD.server.models.entity.User;
import com.TTLTTBDD.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(int id){
        return userRepository.findUsersById(id).map(this::convertToDTO);
    }

    public Optional<UserDTO> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(this::convertToDTO);
    }

    public UserDTO register(User user) {
        Optional<User> existingUser = userRepository.findByUsernameAndEmail(user.getUsername(), user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullname(user.getFullname())
                .address(user.getAddress())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .avata(user.getAvata())
                .build();
    }
}
