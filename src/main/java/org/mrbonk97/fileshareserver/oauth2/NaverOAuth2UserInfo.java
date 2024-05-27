package org.mrbonk97.fileshareserver.oauth2;

import org.mrbonk97.fileshareserver.model.Provider;

import java.util.LinkedHashMap;
import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{

    public NaverOAuth2UserInfo(Map<String, Object> attributes) { super(attributes, Provider.naver); }

    @Override
    String getId() {
        LinkedHashMap<String, String> temp = (LinkedHashMap<String, String>) attributes.get("response");
        return temp.get("id");
    }

    @Override
    String getName() {
        LinkedHashMap<String, String> temp = (LinkedHashMap<String, String>) attributes.get("response");
        return temp.get("name");
    }

    @Override
    String getEmail() {
        LinkedHashMap<String, String> temp = (LinkedHashMap<String, String>) attributes.get("response");
        return temp.get("email");
    }

    @Override
    String getImageUrl() {
        LinkedHashMap<String, String> temp = (LinkedHashMap<String, String>) attributes.get("response");
        return (String) temp.get("profile_image");
    }

    @Override
    Provider getProvider() {
        return Provider.naver;
    }
}
