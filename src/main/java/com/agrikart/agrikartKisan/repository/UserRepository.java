package com.agrikart.agrikartKisan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agrikart.agrikartKisan.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   

    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    List<User> findByRoleNot(String role);
    //Optional<User> findById(Long id);


}
