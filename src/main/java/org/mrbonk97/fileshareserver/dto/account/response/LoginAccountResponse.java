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
    private String refreshToken;

    public static LoginAccountResponse of(Account account) {
        
        //TODO : Token 발급 로직 추가 후 수정 하기
        return new LoginAccountResponse(account.getUsername(),account.getImageUrl(), null, null);
    }

}
