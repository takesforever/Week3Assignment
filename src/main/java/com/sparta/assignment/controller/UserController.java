package com.sparta.assignment.controller;

import com.sparta.assignment.domain.User;
import com.sparta.assignment.dto.SignupRequestDto;
import com.sparta.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequestDto requestDto){
        return userService.registerUser(requestDto);
    }

}
