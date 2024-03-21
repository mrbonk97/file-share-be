package org.mrbonk97.fileshareserver.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.Provider;
import org.mrbonk97.fileshareserver.repository.AccountRepository;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final AccountRepository accountRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);

        Optional<Account> optionalAccount = accountRepository.findByEmail(oAuth2UserInfo.getEmail());
        Account account = null;

        if(optionalAccount.isEmpty())
            account = createOAuth2Account(oAuth2UserInfo, registrationId);

        else if(!optionalAccount.get().getProvider().toString().equalsIgnoreCase(registrationId))
            throw new RuntimeException("다른 OAUTH2 방식으로 회원가입 되어있음.");

        else
            account = updateOAuth2Account(optionalAccount.get(), oAuth2UserInfo);

        return UserPrincipal.create(account);
    }

    private Account createOAuth2Account(OAuth2UserInfo oAuth2UserInfo, String provider) {
        Account account = new Account();
        account.setEmail(oAuth2UserInfo.getEmail());
        account.setProviderId(oAuth2UserInfo.getId());
        account.setUsername(oAuth2UserInfo.getName());
        account.setImageUrl(oAuth2UserInfo.getImageUrl());
        account.setProvider(Provider.valueOf(provider));
        Account savedAccount =  accountRepository.save(account);
        log.info("새로운 OAuth2 유저 생성 완료: " + account.getEmail() + " " + provider);
        return savedAccount;
    }

    private Account updateOAuth2Account(Account existingAccount, OAuth2UserInfo oAuth2UserInfo) {
        existingAccount.setImageUrl(oAuth2UserInfo.getImageUrl());
        existingAccount.setUsername(oAuth2UserInfo.getName());
        Account savedAccount = accountRepository.save(existingAccount);
        log.info("기존의 OAuth2 유저 수정 완료: " + existingAccount.getEmail() + " " + existingAccount.getProvider().toString());
        return savedAccount;
    }
}
