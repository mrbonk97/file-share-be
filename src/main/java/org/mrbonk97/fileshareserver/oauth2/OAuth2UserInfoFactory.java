package org.mrbonk97.fileshareserver.oauth2;

import org.mrbonk97.fileshareserver.model.Provider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
        if(provider.equalsIgnoreCase(Provider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        if(provider.equalsIgnoreCase(Provider.naver.toString())) {
            return new NaverOAuth2UserInfo(attributes);
        }

        if(provider.equalsIgnoreCase(Provider.kakao.toString())) {
            return new KakaoOAuth2UserInfo(attributes);
        }

        throw new RuntimeException("해당 Oauth2 방식은 아직 미지원");
    }


}