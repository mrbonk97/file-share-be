package org.mrbonk97.fileshareserver.oauth2;

import lombok.AllArgsConstructor;
import java.util.Map;

@AllArgsConstructor
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;
    abstract String getId();
    abstract String getName();
    abstract String getEmail();
    abstract String getImageUrl();

}
