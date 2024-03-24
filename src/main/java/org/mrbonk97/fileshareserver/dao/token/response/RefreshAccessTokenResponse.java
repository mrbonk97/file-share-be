package org.mrbonk97.fileshareserver.dao.token.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mrbonk97.fileshareserver.dto.jwt.JwtDto;

@Getter
@AllArgsConstructor
public class RefreshAccessTokenResponse {
    private String accessToken;
    private String refreshToken;

    public static RefreshAccessTokenResponse of(JwtDto jwtDto) {
        return new RefreshAccessTokenResponse(jwtDto.getAccessToken(),jwtDto.getRefreshToken());
    }
}
