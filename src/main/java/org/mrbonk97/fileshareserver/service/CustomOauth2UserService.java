package org.mrbonk97.fileshareserver.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("유저 로그인 완료");
        System.out.println(userRequest.getAccessToken());
        // System.out.println(userRequest.getClientRegistration().toString());
        System.out.println(userRequest.getAdditionalParameters());
        var x = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint();
        var  map = x.getUserNameAttributeName();



        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google



        return super.loadUser(userRequest);
    }
}
