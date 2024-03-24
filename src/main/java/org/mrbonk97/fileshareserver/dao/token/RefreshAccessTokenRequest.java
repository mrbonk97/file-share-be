package org.mrbonk97.fileshareserver.dao.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshAccessTokenRequest {
    private String refreshToken;
}
