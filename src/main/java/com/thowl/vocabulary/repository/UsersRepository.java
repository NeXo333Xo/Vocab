package com.thowl.vocabulary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thowl.vocabulary.entity.Users;


public interface UsersRepository extends JpaRepository<Users, Long> {

}
