package com.agrikart.agrikartKisan.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrikart.agrikartKisan.dto.LoginRequest;
import com.agrikart.agrikartKisan.model.User;
import com.agrikart.agrikartKisan.service.UserService;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
        	System.out.println("Logged in user: " + user.getEmail() + ", Role: " + user.getRole());

            return ResponseEntity.ok(user); // will include role
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
