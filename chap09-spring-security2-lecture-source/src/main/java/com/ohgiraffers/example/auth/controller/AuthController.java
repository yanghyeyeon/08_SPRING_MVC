package com.ohgiraffers.example.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
* 1. /auth/login 주소로 들어오는 요청을 시큐리티에서 처리한다.
* 2. UserDetailsService의 loadByUserName 메서드가 실행, UserDetails 객체를 반환
* 3. UserDetails 정보는 Authentication에 주입
* 4. Authentication SecurityContext에 주입
* */
@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    // 로그인페이지로 이동
    @GetMapping("/login")
    public void login(){}
}
