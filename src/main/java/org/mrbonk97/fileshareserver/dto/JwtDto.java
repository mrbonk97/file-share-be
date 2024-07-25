package org.mrbonk97.fileshareserver.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtDto {
    private final String accessToken;
    private final String refreshToken;
}
