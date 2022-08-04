package com.sparta.assignment.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.assignment.security.provider.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {
    final private ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    public static final String JWT_SECRET = "jwt_secret_!@#$%";


    public FormLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken;
        try {
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());
            String username = requestBody.get("nickname").asText();
            String password = requestBody.get("password").asText();
            authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        } catch (Exception e) {
            throw new RuntimeException("nickname, password 입력이 필요합니다. (JSON)");
        }

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        String access_token = JWT.create()
                    //company name or an author of the token
                    .withIssuer("sparta")
                    .withSubject(user.getUsername())
                    // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                    .sign(generateAlgorithm());


       response.setHeader("access_token", access_token);

        String refresh_token = JWT.create()
                //company name or an author of the token
                .withIssuer("sparta")
                .withSubject(user.getUsername())
                // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .sign(generateAlgorithm());

        response.setHeader("refresh_token", refresh_token);
    }

    private static Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }
}



