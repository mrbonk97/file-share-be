package org.mrbonk97.fileshareserver.dto.account.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
    private String email;
    private String password;
    private String username;
}
