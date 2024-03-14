package org.mrbonk97.fileshareserver.dto.Account.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
    private String email;
    private String password;
    private String username;
}
