package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.dao.Response;
import org.mrbonk97.fileshareserver.dao.token.RefreshAccessTokenRequest;
import org.mrbonk97.fileshareserver.dao.token.response.RefreshAccessTokenResponse;
import org.mrbonk97.fileshareserver.dto.jwt.JwtDto;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.service.RedisService;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {
    private final JwtUtils jwtUtils;
    private final RedisService redisService;

    @GetMapping("/refresh")
    public Response<RefreshAccessTokenResponse> refresh(@RequestBody RefreshAccessTokenRequest refreshAccessTokenRequest) {
        Date issuedDate = jwtUtils.validateTokenAndGetIssuedDate(refreshAccessTokenRequest.getRefreshToken());
        String email = jwtUtils.validateTokenAndGetEmail(refreshAccessTokenRequest.getRefreshToken());

        String _blockedLocalDateTime = redisService.getValue(email);
        if(!_blockedLocalDateTime.equals("false") &&
                issuedDate.before(
                        Date.from(
                                LocalDateTime.parse(_blockedLocalDateTime).atZone(ZoneId.systemDefault()).toInstant())
                ))
        {
            throw new FileShareApplicationException(ErrorCode.BLOCKED_TOKEN);
        }


        JwtDto jwtDto = jwtUtils.refreshAccessToken(refreshAccessTokenRequest.getRefreshToken());
        log.info("사용자가 Jwt Refresh: " + jwtDto.getAccessToken());
        return Response.success(RefreshAccessTokenResponse.of(jwtDto));
    }


}
