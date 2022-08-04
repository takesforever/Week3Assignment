package com.sparta.assignment.security.provider;

import com.sparta.assignment.security.filter.FormLoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true)
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;

    public WebSecurityConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(formLoginFilter());


        http
                //HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정
                .authorizeHttpRequests((authz) -> authz
                        //회원가입
                        .antMatchers("/api/user/signup").permitAll()
                       //로그인
                       .antMatchers("/api/user/login").permitAll()
                        //게시글 목록 로그인 없이 조회
                        .antMatchers("/api/posts").permitAll()
                        //게시글 로그인 없이 조회
                        .antMatchers("/api/posts/{id}").permitAll()
                        //댓글 목록 로그인 없이 조회
                        .antMatchers("/api/comment/{id}").permitAll()
                        .anyRequest().authenticated()  //모두 인증 받아야 함
                )
                .formLogin()
                .loginProcessingUrl("/api/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/api/user/login?error")
                .permitAll();
        return http.build();
    }
    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager);
        formLoginFilter.setFilterProcessesUrl("/user/login");
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }
}