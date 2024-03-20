package org.mrbonk97.fileshareserver.dto.account.response;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.Account;

@RequiredArgsConstructor
public class RegisterAccountResponse {
    private final String email;
    private final String username;
    private final String imageUrl;

    public static RegisterAccountResponse of(Account account) {
        return new RegisterAccountResponse(account.getEmail(), account.getUsername(), account.getImageUrl());
    }
}
