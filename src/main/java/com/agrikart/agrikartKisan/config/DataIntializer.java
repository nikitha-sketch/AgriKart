package com.agrikart.agrikartKisan.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.agrikart.agrikartKisan.model.User;
import com.agrikart.agrikartKisan.repository.UserRepository;

@Configuration
public class DataIntializer {

    @Bean
    CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@agrikart.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setFullName("Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("admin123")); // Password will be securely hashed
                admin.setPhoneNumber("8019797611");
                admin.setAddress("Admin Office");
                admin.setRole("ADMIN");

                userRepository.save(admin);
                System.out.println("✅ Default admin user created: admin@agrikart / admin123");
            } else {
                System.out.println("ℹ️ Admin user already exists.");
            }
        };
    }
}

