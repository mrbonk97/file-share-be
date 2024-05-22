package org.mrbonk97.fileshareserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtils {
    static String secret;
    static Long accessTokenExpireTime;
    static Long refreshTokenExpireTime;
    static SecretKey key;

    public static String generateAccessToken(String id) {
        return Jwts.builder()
                .subject(id)
                .signWith(key)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpireTime))
                .compact();
    }

    public static String generateRefreshToken(String id) {
        return Jwts.builder()
                .subject(id)
                .signWith(key)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpireTime))
                .compact();
    }

    public static Long validateTokenAndGetId(String jwt) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
        System.out.println(claims.getSubject());
        return Long.parseLong(claims.getSubject());
    }

    @Value("${jwt.secret}")
    @EventListener(ApplicationReadyEvent.class)
    public void initSecretKey (Object _secret) {
        secret = (String) _secret;
        byte [] secretBytes = secret.getBytes();
        key = Keys.hmacShaKeyFor(secretBytes);
    }

    @Value("${jwt.access-expire-time}")
    @EventListener(ApplicationReadyEvent.class)
    public void initAccessTokenExpireTime(Object expire) {
        String _accessTokenExpireTime = (String) expire;
        accessTokenExpireTime = Long.parseLong(_accessTokenExpireTime);
    }

    @Value("${jwt.refresh-expire-time}")
    @EventListener(ApplicationReadyEvent.class)
    public void initRefreshTokenExpireTime(Object expire) {
        String _refreshTokenExpireTime = (String) expire;
        refreshTokenExpireTime = Long.parseLong(_refreshTokenExpireTime);
    }



}
