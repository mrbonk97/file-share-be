package org.mrbonk97.fileshareserver.dto.account.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginAccountRequest {
    private final String email;
    private final String password;
}
