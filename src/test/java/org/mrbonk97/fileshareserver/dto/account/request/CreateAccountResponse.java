package org.mrbonk97.fileshareserver.dto.account.request;

import lombok.Getter;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.Account;

@Getter
@Setter
public class CreateAccountResponse {
    private String email;
    private String username;

    public static CreateAccountResponse of(Account account) {
        CreateAccountResponse createAccountResponse = new CreateAccountResponse();
        createAccountResponse.setEmail(account.getEmail());
        createAccountResponse.setUsername(account.getUsername());
        return createAccountResponse;
    }
}
