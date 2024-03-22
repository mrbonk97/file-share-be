package org.mrbonk97.fileshareserver.dto.account.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.FileData;
import org.mrbonk97.fileshareserver.model.Provider;
import org.mrbonk97.fileshareserver.model.UserRole;

import java.util.Date;
import java.util.List;

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
    private Date emailAuthenticated;
    private Date createdAt;
    private Date updatedAt;
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
