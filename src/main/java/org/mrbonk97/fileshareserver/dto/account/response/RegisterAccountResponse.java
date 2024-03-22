package org.mrbonk97.fileshareserver.dto.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.Account;

@Setter
@Getter
@AllArgsConstructor
public class RegisterAccountResponse {
    private String email;
    private String username;
    private String imageUrl;
    private String accessToken;

    public static RegisterAccountResponse of(Account account) {
        return new RegisterAccountResponse(account.getEmail(), account.getUsername(), account.getImageUrl(), account.getAccessToken());
    }
}
