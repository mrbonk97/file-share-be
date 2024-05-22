package org.mrbonk97.fileshareserver.oauth2;

import org.mrbonk97.fileshareserver.model.Provider;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;
    protected Provider provider;
    public OAuth2UserInfo(Map<String, Object> attributes, Provider provider) {
        this.attributes = attributes;
        this.provider = provider;
    }

    abstract String getId();
    abstract String getName();
    abstract String getEmail();
    abstract String getImageUrl();
    abstract Provider getProvider();
}
