package org.mrbonk97.fileshareserver.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.mrbonk97.fileshareserver.dto.jwt.JwtDto;
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

    public String validateTokenAndGetEmail(String jwt){
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(jwt).getPayload().getSubject();
    }

    public Date validateTokenAndGetIssuedDate(String jwt){
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(jwt).getPayload().getIssuedAt();
    }

    public String getEmail(String jwt) {
        String email;
        try{
            email = Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(jwt).getPayload().getSubject();
        } catch (ExpiredJwtException e) {
            email = e.getClaims().getSubject();
        }

        return email;
    }

    @PostConstruct
    public void setSigningKey() {
        byte [] bytes = secret.getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(bytes);
    }

    public JwtDto refreshAccessToken(String jwt) {
        String email = validateTokenAndGetEmail(jwt);
        String accessToken = generateAccessToken(email);
        String refreshToken = generateRefreshToken(email);
        return JwtDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
