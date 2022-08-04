package com.sparta.assignment.domain;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity (name = "Users")
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated (value = EnumType.STRING)
    private UserRoleEnum role;


    public User(String nickname, String password, UserRoleEnum role) {
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}