package org.mrbonk97.fileshareserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.dto.Response;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.service.AccountService;
import org.mrbonk97.fileshareserver.service.RefreshTokenService;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {
    private final RefreshTokenService refreshTokenService;
    private final AccountService accountService;
    private final JwtUtils jwtUtils;
    @GetMapping("/refresh")
    public Response<String> refresh(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String expiredAccessToken = header.split(" ")[1].trim();
        String email = jwtUtils.getEmil(expiredAccessToken);
        Account account = accountService.loadByEmail(email);
        String refreshedAccessToken = refreshTokenService.refreshAccessToken(account);
        log.info(email + "을 가진 사용자가 Jwt Refresh: " + refreshedAccessToken);
        return Response.success(refreshedAccessToken);
    }
}
