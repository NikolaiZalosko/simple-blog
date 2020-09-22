package com.nickz.blog.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    
    @NotEmpty(message = "Username can't be empty")
    private String username;
    
    @NotEmpty(message = "Password can't be empty")
    private String password;
}
