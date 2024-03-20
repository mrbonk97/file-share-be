package org.mrbonk97.fileshareserver.dto.account.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.Account;

@Getter
@RequiredArgsConstructor
public class LoginAccountResponse {
    private final String username;
    private final String imageUrl;
    private final String accessToken;
    private final String refreshToken;

    public static LoginAccountResponse of(Account account) {
        
        //TODO : Token 발급 로직 추가 후 수정 하기
        return new LoginAccountResponse(account.getUsername(),account.getImageUrl(), null, null);
    }

}
