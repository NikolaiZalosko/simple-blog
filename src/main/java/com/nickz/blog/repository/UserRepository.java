package com.nickz.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nickz.blog.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
