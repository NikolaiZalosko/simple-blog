package com.nickz.blog.dto;

import javax.validation.constraints.NotEmpty;

import com.nickz.blog.constraint.FieldMatch;

import lombok.Getter;

import lombok.Setter;

@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords are not equal")
@Getter
@Setter
public class UserRegistrationDto {

    @NotEmpty(message = "Username can't be empty")
    private String username;

    @NotEmpty(message = "Password can't be empty")
    private String password;

    @NotEmpty(message = "Password can't be empty")
    private String confirmPassword;
}
