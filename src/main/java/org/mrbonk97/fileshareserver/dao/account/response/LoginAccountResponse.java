package org.mrbonk97.fileshareserver.dao.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.Provider;
import org.mrbonk97.fileshareserver.model.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LoginAccountResponse {

    private Long id;
    private String email;
    private String username;
    private String imageUrl;
    private Provider provider;
    private String providerId;
    private UserRole userRole;
    private LocalDateTime emailAuthenticated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String accessToken;


    public static LoginAccountResponse of(Account account) {
        return new LoginAccountResponse(
                account.getId(),
                account.getEmail(),
                account.getUsername(),
                account.getImageUrl(),
                account.getProvider(),
                account.getProviderId(),
                account.getUserRole(),
                account.getEmailAuthenticated(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getAccessToken()
        );

    }

}
