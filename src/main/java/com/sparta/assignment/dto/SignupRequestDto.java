package com.sparta.assignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String nickname;
    private String password;
    private String confirmedPassword;
    private boolean user = true;
}
