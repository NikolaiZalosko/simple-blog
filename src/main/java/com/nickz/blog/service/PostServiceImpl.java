package com.nickz.blog.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nickz.blog.model.Post;
import com.nickz.blog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
	return postRepository.findAll();
    }

    @Override
    public void delete(int id) {
	postRepository.deleteById(id);
    }

    @Override
    public Post save(Post post) {
	post.setCreationDate(ZonedDateTime.now());
	postRepository.save(post);
	return post;
    }

}
