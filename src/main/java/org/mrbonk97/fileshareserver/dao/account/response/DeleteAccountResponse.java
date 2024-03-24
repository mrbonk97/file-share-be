package org.mrbonk97.fileshareserver.dao.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DeleteAccountResponse {
    public String message;

    public static DeleteAccountResponse of(String message) {
        return new DeleteAccountResponse(message);
    }

}
