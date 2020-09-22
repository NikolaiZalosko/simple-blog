package com.nickz.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Role implements GrantedAuthority {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String name;

    public Role(String name) {
	super();
	this.name = name;
    }

    @Override
    public String getAuthority() {
	return getName();
    }
    
    
    
}
