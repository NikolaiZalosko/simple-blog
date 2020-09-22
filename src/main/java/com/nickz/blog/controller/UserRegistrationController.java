package com.nickz.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nickz.blog.dto.UserRegistrationDto;
import com.nickz.blog.model.User;
import com.nickz.blog.service.UserService;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {
    
    @Autowired
    private UserService userService;
    
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
	return new UserRegistrationDto();
    }
    
    /*
     * Show registration page
     */
    @GetMapping
    public String showRegistrationPage() {
	return "register";
    }
    
    /*
     * Register a user
     */
    @PostMapping()
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result) {
	User existingUser = userService.findByUsername(userDto.getUsername());
	if (existingUser != null) {
	    result.rejectValue("username", null, "Username already in use");
	}
	if (result.hasErrors()) {
	    return "register";
	}
	userService.save(userDto);
	return "redirect:/login";
    }
}
