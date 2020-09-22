package com.nickz.blog.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nickz.blog.dto.UserRegistrationDto;
import com.nickz.blog.model.User;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    List<User> findAll();
    User save(UserRegistrationDto userDto);
    boolean delete(int id);
}
