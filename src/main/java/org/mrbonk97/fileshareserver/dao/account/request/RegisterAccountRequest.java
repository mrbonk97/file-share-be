package org.mrbonk97.fileshareserver.dao.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterAccountRequest {
    private String email;
    private String password;
    private String username;
}
