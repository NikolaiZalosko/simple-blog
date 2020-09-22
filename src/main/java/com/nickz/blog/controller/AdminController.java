package com.nickz.blog.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nickz.blog.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(Model model) {
	model.addAttribute("users", userService.findAll());
	return "admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") int id, Model model,
	    Principal principal) {
	User loginedUser = (User) ((Authentication) principal).getPrincipal();
	int loginedUserId = userService.findByUsername(loginedUser.getUsername()).getId();
	userService.delete(id);
	if (id == loginedUserId) {
	    return "redirect:/login?deleted";
	}
	return "redirect:/admin";
    }

}
