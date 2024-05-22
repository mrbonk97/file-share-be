package org.mrbonk97.fileshareserver.dao.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.User;

@Setter
@Getter
@AllArgsConstructor
public class RegisterAccountResponse {
    private String email;
    private String username;
    private String imageUrl;
    private String accessToken;
    private String refreshToken;

    public static RegisterAccountResponse of(User user) {
        return new RegisterAccountResponse(user.getEmail(), user.getUsername(), user.getImageUrl(), user.getAccessToken(), user.getRefreshToken());
    }
}
