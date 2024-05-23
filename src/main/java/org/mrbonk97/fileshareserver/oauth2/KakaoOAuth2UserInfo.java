package org.mrbonk97.fileshareserver.oauth2;

import org.mrbonk97.fileshareserver.model.Provider;

import java.util.LinkedHashMap;
import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) { super(attributes, Provider.kakao); }

    @Override
    String getId() {
        return attributes.get("id").toString();
    }

    @Override
    String getName() {
        LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>) attributes.get("kakao_account");
        LinkedHashMap<String, Object> temp2 = (LinkedHashMap<String, Object>) temp.get("profile");
        return temp2.get("nickname").toString();
    }

    @Override
    String getEmail() {
        LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>) attributes.get("kakao_account");
        return temp.get("email").toString();
    }

    @Override
    String getImageUrl() {
        LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>) attributes.get("kakao_account");
        LinkedHashMap<String, Object> temp2 = (LinkedHashMap<String, Object>) temp.get("profile");
        return temp2.get("profile_image_url").toString();
    }

    @Override
    Provider getProvider() {
        return Provider.kakao;
    }
}
