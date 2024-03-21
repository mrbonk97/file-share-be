package org.mrbonk97.fileshareserver.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtUtils {
    @Value("${jwt.accessTokenExpireDate}")
    private Long accessTokenExpireDate;
    @Value("${jwt.refreshTokenExpireDate}")
    private Long refreshTokenExpireDate;
    @Value("${jwt.secret}")
    private String secret;
    SecretKey signingKey;

    public String generateAccessToken(String email) {
        return Jwts
                .builder()
                .subject(email)
                .signWith(signingKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpireDate))
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts
                .builder()
                .subject(email)
                .signWith(signingKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpireDate))
                .compact();
    }

    public String validateToken(String jwt) throws ExpiredJwtException{
            return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(jwt).getPayload().getSubject();
    }

    @PostConstruct
    public void setSigningKey() {
        byte [] bytes = secret.getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(bytes);
    }

}
