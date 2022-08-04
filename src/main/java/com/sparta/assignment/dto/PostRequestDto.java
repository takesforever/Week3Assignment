package com.sparta.assignment.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String author;
    private String password;
    private String content;
}
