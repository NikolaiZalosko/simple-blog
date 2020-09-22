package com.nickz.blog.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nickz.blog.dto.PostCreationDto;
import com.nickz.blog.dto.UserLoginDto;
import com.nickz.blog.model.Post;
import com.nickz.blog.service.PostService;
import com.nickz.blog.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @ModelAttribute("user")
    public UserLoginDto userLoginDto() {
	return new UserLoginDto();
    }

    @ModelAttribute("newPost")
    public PostCreationDto postCreatoinDto() {
	return new PostCreationDto();
    }

    /*
     * Home page
     */
    @GetMapping
    public String showHomepage(Model model, Principal principal) {
	User loginedUser = (User) ((Authentication) principal).getPrincipal();
	List<Post> posts = postService.findAll();
	Collections.reverse(posts);
	model.addAttribute("posts", posts);
	model.addAttribute("user", loginedUser);
	return "index";
    }

    /*
     * Create a new post
     */
    @PostMapping("/posts")
    public String createPost(@ModelAttribute("newPost") @Valid PostCreationDto postDto, BindingResult result,
	    Principal principal, Model model, HttpServletRequest req) {
	if (result.hasErrors()) {
	    System.err.println("result has errors");
	    return "redirect:/./";
	}
	Post post = new Post();
	post.setText(postDto.getText());
	User loginedUser = (User) ((Authentication) principal).getPrincipal();
	post.setAuthor(userService.findByUsername(loginedUser.getUsername()));
	postService.save(post);
	return "redirect:/";
    }

    /*
     * Login page
     */
    @GetMapping("/login")
    public String showLoginPage() {
	return "login";
    }

}
