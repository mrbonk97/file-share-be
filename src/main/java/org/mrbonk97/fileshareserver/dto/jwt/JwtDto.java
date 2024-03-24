package org.mrbonk97.fileshareserver.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
