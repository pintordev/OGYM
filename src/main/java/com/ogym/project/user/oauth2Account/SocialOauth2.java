package com.ogym.project.user.oauth2Account;

import java.util.Map;

public interface SocialOauth2 {

    String getRedirectUrlCode();

    String getAccessToken(String code, String state);

    Map<String, Object> getUserAttribute(String accessToken);
}
