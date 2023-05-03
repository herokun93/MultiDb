package com.multi.demo.controller;


import com.multi.demo.entity.social.Post;
import com.multi.demo.repository.social.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAll() {

        List<Post> posts = postRepository.findAll();
        System.out.println(posts.size());

        return new ResponseEntity<>( posts.toString(), HttpStatus.OK);
    }
}
