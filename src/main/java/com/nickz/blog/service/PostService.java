package com.nickz.blog.service;

import java.util.List;

import com.nickz.blog.model.Post;

public interface PostService {
    List<Post> findAll();

    void delete(int id);

    Post save(Post post);
}
