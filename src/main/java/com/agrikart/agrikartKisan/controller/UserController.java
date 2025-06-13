package com.agrikart.agrikartKisan.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrikart.agrikartKisan.dto.UserDTO;
import com.agrikart.agrikartKisan.model.User;
import com.agrikart.agrikartKisan.repository.UserRepository;
import com.agrikart.agrikartKisan.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Register new user
    @PostMapping("/sign_up")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDTO userDto) {
        User registeredUser = userService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully with email: " + registeredUser.getEmail());
    }

    // Login user
 // Login user
   /*@PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> userOpt = userService.loginUser(email, password);  // ✅ FIXED
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("fullName", user.getFullName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }*/


}


