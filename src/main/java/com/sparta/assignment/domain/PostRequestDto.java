package com.sparta.assignment.domain;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String author;
    private String password;
    private String content;
}
