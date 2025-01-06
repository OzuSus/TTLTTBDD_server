package com.TTLTTBDD.server.services;

import com.TTLTTBDD.server.models.dto.UserDTO;
import com.TTLTTBDD.server.models.entity.Cart;
import com.TTLTTBDD.server.models.entity.User;
import com.TTLTTBDD.server.repositories.CartRepository;
import com.TTLTTBDD.server.repositories.UserRepository;
import com.TTLTTBDD.server.utils.loadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    private loadFile loadFile = new loadFile();
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(int id){
        return userRepository.findUsersById(id).map(this::convertToDTO);
    }

    public Optional<UserDTO> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return Optional.of(convertToDTO(user.get()));
        }
        return Optional.empty();
    }

    public UserDTO register(User user) {
        Optional<User> existingUser = userRepository.findByUsernameAndEmail(user.getUsername(), user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);
        Cart newCart = new Cart();
        newCart.setIdUser(savedUser);
        cartRepository.save(newCart);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUserInfoAccount(UserDTO userDTO) {
            User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("Ko tìm thấy user"));
            user.setUsername(userDTO.getUsername());
            user.setFullname(userDTO.getFullname());
            user.setAddress(userDTO.getAddress());
            user.setPhone(userDTO.getPhone());
            user.setEmail(userDTO.getEmail());

            userRepository.save(user);
            return convertToDTO(user);

    }

    public UserDTO updateUserAvata(UserDTO userDTO, MultipartFile avataFile) {
        try {
            if (avataFile != null && !avataFile.isEmpty()) {
                String avatarPath = loadFile.saveFile(avataFile);
                userDTO.setAvata(avatarPath);
            }
            User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("Ko tìm thấy user"));
            user.setAvata(userDTO.getAvata());

            userRepository.save(user);
            return convertToDTO(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar", e);
        }
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
