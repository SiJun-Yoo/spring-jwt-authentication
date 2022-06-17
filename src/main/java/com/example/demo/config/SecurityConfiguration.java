package com.example.demo.config;

import com.example.demo.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity security) throws Exception {
        return security
                .cors() //cors 허용?
                .and()
                .csrf()
                .disable()  //csrf토큰을 사용하지 않겠다.
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint) // filter에서 예외가 발생했을 때 핸들링
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 세션을 사용하지 않겠다!!! 만들지도 않겠다!!!
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()//인증을 요청할 때 허용
                .antMatchers("/user/join").permitAll()//인증을 요청할 때 허용
                .anyRequest().authenticated()//이외의 권한 확인
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
