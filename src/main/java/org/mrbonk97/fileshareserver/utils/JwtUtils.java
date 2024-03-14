package org.mrbonk97.fileshareserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    Long expireDate = 100_000_000_000L;
    @Value("${jwt.secret}")
    String secret;
    public void generateToken(String email, Key key) {
        Jwts.builder()
                .issuedAt(new Date())
                .subject(email)
                .signWith(key)
                .expiration(new Date())
                .compact();
    }

    public void generateKey() {

    }
}
