package org.mrbonk97.fileshareserver.dto.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.Account;

@Getter
@Setter
@AllArgsConstructor
public class LoginAccountResponse {
    private String username;
    private String imageUrl;
    private String accessToken;


    public static LoginAccountResponse of(Account account, String accessToken) {
        return new LoginAccountResponse(account.getUsername(),account.getImageUrl(), accessToken);

    }

}
