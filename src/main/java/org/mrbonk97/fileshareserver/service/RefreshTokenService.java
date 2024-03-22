package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.RefreshToken;
import org.mrbonk97.fileshareserver.repository.AccountRepository;
import org.mrbonk97.fileshareserver.repository.RefreshTokenRepository;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;
    @Value("${jwt.refreshTokenExpireDate}")
    private Long refreshTokenExpireDate;

    public void createRefreshToken(Account account) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAccount(account);
        refreshToken.setIssuedDate(new Date());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + refreshTokenExpireDate));
        refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshToken(Account account) {
        refreshTokenRepository.deleteByAccount(account);
    }

    public String refreshAccessToken(Account account) {
        RefreshToken refreshToken = refreshTokenRepository.findByAccount(account).orElseThrow(() -> new RuntimeException("DB에 리프레시 토큰 없음"));
        if(new Date().before(refreshToken.getExpireDate()))
        {
            String accessToken = jwtUtils.generateAccessToken(account.getAccessToken());
            refreshToken.setIssuedDate(new Date());
            refreshToken.setExpireDate(new Date(System.currentTimeMillis() + refreshTokenExpireDate));
            refreshTokenRepository.save(refreshToken);
            return accessToken;
        }
        else throw new RuntimeException("리프레시 토큰 만료.");
    }


}
