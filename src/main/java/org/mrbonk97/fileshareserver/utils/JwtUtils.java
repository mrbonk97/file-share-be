package org.mrbonk97.fileshareserver.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Ref;
import java.util.Date;


@Component
public class JwtUtils {
    @Value("${jwt.accessTokenExpireDate}")
    private Long accessTokenExpireDate;
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

    public String validateToken(String jwt){
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(jwt).getPayload().getSubject();
    }

    public String getEmil(String jwt) {
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

}
