package com.agrikart.agrikartKisan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrikart.agrikartKisan.dto.UserDTO;
import com.agrikart.agrikartKisan.model.User;
import com.agrikart.agrikartKisan.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register user using UserDto
    public User registerUser(UserDTO userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail().toLowerCase());
        user.setPassword(userDto.getPassword());

        // Set role (default to USER if not provided)
        if (userDto.getRole() == null || userDto.getRole().isEmpty()) {
            user.setRole("USER");
        } else {
            user.setRole(userDto.getRole().toUpperCase());
        }

        return userRepository.save(user);
    }

    // Login user using email and password
    public User loginUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
    /*public List<User> getAllUsers() {
        return userRepository.findAll();
    }*/
    public List<User> getAllNonAdminUsers() {
        return userRepository.findByRoleNot("admin");
    }


}
