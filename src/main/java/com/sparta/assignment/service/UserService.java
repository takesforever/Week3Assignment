package com.sparta.assignment.service;

import com.sparta.assignment.domain.User;
import com.sparta.assignment.domain.UserRoleEnum;
import com.sparta.assignment.dto.SignupRequestDto;
import com.sparta.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User registerUser(SignupRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String rawPassword = requestDto.getPassword();
        String confirmedPassword = requestDto.getConfirmedPassword();
        UserRoleEnum role = UserRoleEnum.USER;

        if (nickname.matches("^[A-Za-z0-9]{3,13}$")) {
            Optional<User> found = userRepository.findByNickname(nickname);
            if (found.isPresent()) {
                throw new IllegalArgumentException("중복된 닉네임입니다.");
            }
        }else{
            throw new IllegalArgumentException("닉네임을 조건에 맞게 써주세요.");
        }

        if (rawPassword.matches("^[a-z0-9]{3,33}$")) {

            if (!rawPassword.equals(confirmedPassword)) {
                throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }
        }else {
            throw new IllegalArgumentException("비밀번호를 조건에 맞게 써주세요.");
        }

        String password = passwordEncoder.encode(rawPassword);
        

        User user = new User(nickname, password, role);
        return userRepository.save(user);
    }
}
