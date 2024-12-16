package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 설정 파일임을 의미
@EnableWebSecurity // 스프링 시큐리티 활성화(모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만듦)
public class SecurityConfig {

    // 스프링 시큐리티의 세부 설정은 @Bean 애너테이션을 통해 SecurityFilterChain 빈을 생성하여 설정(bean: 스프링에 의해 생성 또는 관리되는 객체)

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 인증되지 않은 모든 페이지의 요청을 허락.(로그인하지 않아도 모든 페이지에 접근할 수 있음)
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                // /h2-console/로 시작하는 모든 URL은 CSRF 검증을 하지 않음.
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                // XFrameOptionsMode.SAMEORIGIN: 프레임에 포함된 웹 페이지가 동일한 사이트에서 제공할 때에만 사용이 허락됨.(기본값: DENY. 클릭재킹 막기 위함.)
                .headers(headers -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


