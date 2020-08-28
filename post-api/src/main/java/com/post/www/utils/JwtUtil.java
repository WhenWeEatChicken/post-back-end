package com.post.www.utils;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long userId, String name) {
        JwtBuilder builder = Jwts.builder()
                .claim("userId",userId)
                .claim("name", name);

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
