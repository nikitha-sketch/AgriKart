package com.agrikart.agrikartKisan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for REST login
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/**",                 // Allow everything (adjust if needed)
                    "/login",              // Your custom login endpoint
                    "/signup",
                    "/admin.html",
                    "/index.html",
                    "/login.html",
                    "/sign_up.html",
                    "/js/**",
                    "/css/**",
                    "/images/**"
                ).permitAll()
                .anyRequest().permitAll() // Allow all other routes
            )
            .formLogin(form -> form.disable()) // Disable Spring Security login
            .httpBasic(basic -> basic.disable()); // Disable Basic Auth

        return http.build();
    }
}
