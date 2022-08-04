package com.sparta.assignment.repository;

import com.sparta.assignment.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository <Post, Long> {
        List<Post> findAllByOrderByCreatedAtDesc();
}
