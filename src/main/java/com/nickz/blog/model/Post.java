package com.nickz.blog.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    public Post(String text, User author, ZonedDateTime creationDate) {
	this.text = text;
	this.author = author;
	this.creationDate = creationDate;
    }

    public String getCreationMonth() {
	String rawMonth = this.getCreationDate().getMonth().toString();
	String result = rawMonth.substring(0, 1) + rawMonth.substring(1).toLowerCase();
	return result;
    }

}
