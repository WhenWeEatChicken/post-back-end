package com.post.www.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private Key key;
    private Long tokenUseTimeMiliSecond = 2000L * 60 * 60; //2시간

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long userId, String name) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .setIssuedAt(now) //발행일자
                .setExpiration(new Date(now.getTime() + tokenUseTimeMiliSecond));//만료일자

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //토큰의 정보 추출
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token) //jws 란 sign이 포함된 jwt 의미
                .getBody();
    }
}
