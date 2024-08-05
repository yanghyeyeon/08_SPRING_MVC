package com.ohgiraffers.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // security에서 특정 경로를 보안 검증에서 제외하는 코드
    // CSS, JS, 이미지 같은 정적 자원들에 대해 보안 필터를 적용하지 않게 함.
    @Bean
    public WebSecurityCustomizer securityCustomizer() {
//        return new WebSecurityCustomizer() {
//            @Override
//            public void customize(WebSecurity web) {
//                web.ignoring().requestMatchers("/css/**","/js/**","/images/**");
//            }
//        };
        return (web) -> web
                .ignoring()
                .requestMatchers(PathRequest
                .toStaticResources()
                .atCommonLocations());
    }

    // Spring Security에서 제공하는 인증, 인가를 위한 필터들의 모음
    // 기본적으로 제공하는 필터들이 있고, 커스텀 필터또한 적용 시킬 수 있다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/","/index.html").permitAll() // 모두에게 허용
                    .requestMatchers("/member/register").anonymous() // 비인증사용자만 접근
                    .requestMatchers("/post/**").authenticated()
                    .requestMatchers("/admin/**").hasRole("ADMIN") // ROLE이 ADMIN인 경우만 접근 가능
                    .anyRequest().authenticated(); // 인증된 사용자만 요청가능
        }));

        // formLogin 설정
        http.formLogin((formLoginConfigurer -> {
            formLoginConfigurer
                    .loginPage("/auth/login") // 로그인 페이지 (GET)
                    .loginProcessingUrl("/auth/login") // 로그인처리(POST)
                    .usernameParameter("memberId") // userName으로 전달할 파라미터 설정
                    .passwordParameter("password") // password로 전달할 파라미터 설정
                    .defaultSuccessUrl("/") // 로그인 성공시 이동할 url
                    .permitAll();
        }));

        http.logout(logoutConfigurer -> {
            logoutConfigurer
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/");
        });

        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
