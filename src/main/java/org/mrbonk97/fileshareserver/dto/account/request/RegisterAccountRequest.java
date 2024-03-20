package org.mrbonk97.fileshareserver.dto.account.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountRequest {
    private String email;
    private String password;
    private String username;
}
