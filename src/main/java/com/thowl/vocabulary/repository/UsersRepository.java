package com.thowl.vocabulary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thowl.vocabulary.entity.Users;


public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndPassword(String username, String password);
    boolean existsByEmailOrUsername(String email, String username);
    Users findByEmail(String email);
    Users findByUsername(String username);
    Users findByUsernameAndPassword(String username, String password);         

}
