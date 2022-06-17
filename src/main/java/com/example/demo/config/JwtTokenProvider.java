package com.example.demo.config;

import com.example.demo.domain.LoginInfo;
import com.example.demo.service.LoginInfoService;
import com.example.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final long TOKEN_VALID_SECOND = 1000L * 60 * 30; //토큰 유효기간 30분

    private static final String ACCESS_TOKEN_NAME = "accessToken";

    @Value("spring.jwt.secret")
    private String secretKey;

    private final LoginInfoService loginInfoService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userId, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles",roles);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TOKEN_VALID_SECOND))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    public Authentication getAuthentication(String token){
        String userId = getUserId(token);
        LoginInfo loginInfo = loginInfoService.loadUserByUserId(userId);
        return new UsernamePasswordAuthenticationToken(loginInfo,"",loginInfo.getAuthorities());
    }

    public String getUserId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(JwtTokenProvider.ACCESS_TOKEN_NAME);
    }

    public boolean validateToken(String jwtToken){
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        return !claims.getBody().getExpiration().before(new Date());
    }
}
