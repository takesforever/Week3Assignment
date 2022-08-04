package com.sparta.assignment.controller;

import com.sparta.assignment.domain.Post;
import com.sparta.assignment.dto.PostRequestDto;
import com.sparta.assignment.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostRestController {


    private final PostService postService;

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postService.getAll();
    }

    @GetMapping("/posts/{id}")
    public Post getOnePosts(@PathVariable Long id) {
       return postService.getOne(id);
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        return postService.postPost(requestDto);
    }

    @PostMapping("/posts/{id}")
    public Long checkPost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.matchPw(id, requestDto);
    }

    @PutMapping("/posts/{id}")
    public Post editPost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.delete(id);
    }
}