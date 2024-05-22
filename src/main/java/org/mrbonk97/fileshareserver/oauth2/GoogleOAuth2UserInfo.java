package org.mrbonk97.fileshareserver.oauth2;


import org.mrbonk97.fileshareserver.model.Provider;

import java.util.Map;


public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes, Provider.google);
    }

    @Override
    String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    String getName() {
        return (String) attributes.get("name");
    }

    @Override
    String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    Provider getProvider() {
        return Provider.google;
    }
}
