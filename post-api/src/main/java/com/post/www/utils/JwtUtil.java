package com.post.www.utils;


import com.post.www.application.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long userId, String name) {
        Date now = new Date();
        Long tokenUseTime = 2000L * 60 * 60; //2시간
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .setIssuedAt(now) //발행일자
                .setExpiration(new Date(now.getTime() + tokenUseTime));//만료일자

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //토큰의 정보 추출
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token) //jws 란 sign이 포함된 jwt 의미
                    .getBody();
        }catch (ExpiredJwtException e){
            throw new TokenExpiredException();
        }
    }


}
