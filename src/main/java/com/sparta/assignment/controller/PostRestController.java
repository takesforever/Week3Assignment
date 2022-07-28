package com.sparta.assignment.controller;

import com.sparta.assignment.domain.Post;
import com.sparta.assignment.domain.PostRepository;
import com.sparta.assignment.domain.PostRequestDto;
import com.sparta.assignment.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;

    @GetMapping("/api/posts")
    public List<Post> getPosts() {
        return postService.getAll();
    }

    @GetMapping("/api/posts/{id}")
    public Post getOnePosts(@PathVariable Long id) {
       return postService.getOne(id);
    }

    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        return postService.postPost(requestDto);
    }

    @PostMapping("/api/posts/{id}")
    public Long checkPost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.matchPw(id, requestDto);
    }

    @PutMapping("/api/posts/{id}")
    public Post editPost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.delete(id);
    }
}