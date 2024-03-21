package org.mrbonk97.fileshareserver.oauth2;

import org.mrbonk97.fileshareserver.model.Provider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(Provider.google.toString()))
            return new GoogleOAuth2UserInfo(attributes);

        if(registrationId.equalsIgnoreCase(Provider.kakao.toString()))
            return new GoogleOAuth2UserInfo(attributes);

        if(registrationId.equalsIgnoreCase(Provider.naver.toString()))
            return new GoogleOAuth2UserInfo(attributes);
        
        throw new RuntimeException("대충 알수없는 OAuth2 client 정보");
        
        // TODO : Naver 및 Kakao OAuth2 구현, 커스텀 에러 메세지 구현

    }


}
