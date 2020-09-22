package com.nickz.blog.service;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nickz.blog.dto.UserRegistrationDto;
import com.nickz.blog.exception.ResourceNotFoundException;
import com.nickz.blog.model.Role;
import com.nickz.blog.model.User;
import com.nickz.blog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findById(int id) throws ResourceNotFoundException {
	return userRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    public List<User> findAll() {
	return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = userRepository.findByUsername(username);
	if (user == null) {
	    throw new UsernameNotFoundException("Invalid username or password");
	}
	return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
		mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
	return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
	return userRepository.findByUsername(username);
    }

    @Override
    public User save(UserRegistrationDto userDto) {
	User user = new User();
	user.setUsername(userDto.getUsername());
	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
	user.setRegisterDate(ZonedDateTime.now());
	user.setRoles(Arrays.asList(new Role("ROLE_USER")));
	return userRepository.save(user);
    }

    public boolean delete(int id) {
	if (userRepository.findById(id).isPresent()) {
	    userRepository.deleteById(id);
	    return true;
	}
	return false;
    }

}
