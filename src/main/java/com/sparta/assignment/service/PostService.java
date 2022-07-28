package com.sparta.assignment.service;

import com.sparta.assignment.domain.Post;
import com.sparta.assignment.domain.PostRepository;
import com.sparta.assignment.domain.PostRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.*;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public List<Post> getAll() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Post getOne(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("Post does not exist")
        );
        return post;
    }

    @Transactional
    public Post postPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        return postRepository.save(post);
    }

    @Transactional
    public Long matchPw(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("포스트가 없습니다.")
        );
        if (post.getPassword().equals(requestDto.getPassword())) {
            return id;
        } else {
            return null;
        }
    }

    @Transactional
    public Post updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("포스트가 없습니다.")
        );
        post.update(requestDto);
        return post;
    }

    @Transactional
    public Long delete(Long id) {
        postRepository.deleteById(id);
        return id;
    }
}


