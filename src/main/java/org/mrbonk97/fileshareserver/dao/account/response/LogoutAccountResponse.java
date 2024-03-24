package org.mrbonk97.fileshareserver.dao.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LogoutAccountResponse {
    public String message;

    public static LogoutAccountResponse of(String message) {
        return new LogoutAccountResponse(message);
    }

}
