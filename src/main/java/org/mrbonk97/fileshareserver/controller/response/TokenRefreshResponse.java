package org.mrbonk97.fileshareserver.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class TokenRefreshResponse {
    private final String access_token;
    public static TokenRefreshResponse of(String accessToken) {
        return new TokenRefreshResponse(accessToken);
    }
}
