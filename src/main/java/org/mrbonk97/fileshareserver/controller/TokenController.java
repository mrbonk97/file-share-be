package org.mrbonk97.fileshareserver.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.controller.response.TokenRefreshResponse;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.utils.CookieUtils;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    @GetMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> getAccessToken(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request, "refresh_token").orElseThrow(() -> new FileShareApplicationException(ErrorCode.TOKEN_NOT_FOUND));
        String _refresh_token = cookie.getValue();
        System.out.println(_refresh_token);
        Long id = JwtUtils.validateTokenAndGetId(_refresh_token);

        String refreshToken = JwtUtils.generateRefreshToken(id);
        String accessToken = JwtUtils.generateAccessToken(id);
        ResponseCookie responseCookie = CookieUtils.generateRefreshToken(refreshToken);

        log.info("유저: {}, 토큰 새로 고침", id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
                .body(TokenRefreshResponse.of(accessToken));
    }
}
