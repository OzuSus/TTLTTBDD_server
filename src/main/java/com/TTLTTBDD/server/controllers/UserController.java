package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.models.dto.UserDTO;
import com.TTLTTBDD.server.models.entity.User;
import com.TTLTTBDD.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/id")
    public Optional<UserDTO> getUserById(@RequestParam int id){
        return userService.getUserById(id);
    }

    @CrossOrigin(origins = {"http://localhost:31415"})
    @PostMapping("/login")
    public Optional<UserDTO> login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @CrossOrigin(origins = {"http://localhost:31415"})
    @PostMapping("/register")
    public UserDTO register(@RequestBody User user) {
        return userService.register(user);
    }
}
