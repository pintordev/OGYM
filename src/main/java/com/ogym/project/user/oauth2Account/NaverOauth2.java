package com.ogym.project.user.oauth2Account;

import com.ogym.project.user.user.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NaverOauth2 implements SocialOauth2 {

    private final UserService userService;

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri-code}")
    private String redirectUriCode;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String userInfoUri;

    @Override
    public String getRedirectUrlCode() {

        Map<String, Object> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", clientId);
        params.put("state", this.userService.genConfirmCode(12));
        params.put("redirect_uri", redirectUriCode);

        String paramsString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return authorizationUri + "?" + paramsString;
    }

    @Override
    public String getAccessToken(String code, String state) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "authorization_code");
        params.set("client_id", clientId);
        params.set("client_secret", clientSecret);
        params.set("code", code);
        params.set("state", state);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(tokenUri, restRequest, JSONObject.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return (String) responseEntity.getBody().get("access_token");
        }

        return "";
    }

    @Override
    public Map<String, Object> getUserAttribute(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(httpHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(userInfoUri, HttpMethod.GET, restRequest, JSONObject.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userAttributes = new HashMap<>();
            Map<String, Object> response = (Map) responseEntity.getBody().get("response");
            userAttributes.put("provider", "naver");
            userAttributes.put("providerId", response.get("id"));
            userAttributes.put("email", response.get("email"));
            userAttributes.put("name", response.get("name"));

            return userAttributes;
        }

        return new HashMap<>();
    }
}
