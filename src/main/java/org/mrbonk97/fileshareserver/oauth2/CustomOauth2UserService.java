package org.mrbonk97.fileshareserver.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.model.Provider;
import org.mrbonk97.fileshareserver.repository.AccountRepository;
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

        Optional<User> optionalAccount = accountRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user = null;

        if(optionalAccount.isEmpty())
            user = createOAuth2Account(oAuth2UserInfo, registrationId);

        else if(!optionalAccount.get().getProvider().toString().equalsIgnoreCase(registrationId))
            throw new RuntimeException("다른 OAUTH2 방식으로 회원가입 되어있음.");

        else
            user = updateOAuth2Account(optionalAccount.get(), oAuth2UserInfo);

        return user;
    }

    private User createOAuth2Account(OAuth2UserInfo oAuth2UserInfo, String provider) {
        User user = new User();
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getName());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.setProvider(oAuth2UserInfo.getProvider());
        User savedUser = accountRepository.save(user);
        log.info("새로운 OAuth2 유저 생성 완료: " + user.getEmail() + " " + provider);
        return savedUser;
    }

    private User updateOAuth2Account(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        existingUser.setUsername(oAuth2UserInfo.getName());
        User savedUser = accountRepository.save(existingUser);
        log.info("기존의 OAuth2 유저 수정 완료: " + existingUser.getEmail() + " " + existingUser.getProvider().toString());
        return savedUser;
    }
}
