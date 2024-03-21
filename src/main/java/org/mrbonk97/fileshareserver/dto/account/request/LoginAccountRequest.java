package org.mrbonk97.fileshareserver.dto.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginAccountRequest {
    private String email;
    private String password;
}
