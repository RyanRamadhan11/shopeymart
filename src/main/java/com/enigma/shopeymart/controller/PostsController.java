package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.service.impl.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;

    //ini untuk get all default
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> getAllPosts(){
//        return postService.getAllPostsDefault();
//    }

    //ini untuk get all gabungan dari db local dan db open api
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Posts>> getAllPostCombine(){
        return postService.getAllPostsCombine();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPostsById(@PathVariable Long id){
        return postService.getPostsById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCreatePosts(@RequestBody Posts posts){
        return postService.createPosts(posts);
    }
}
