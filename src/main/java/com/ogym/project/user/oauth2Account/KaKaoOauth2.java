package com.ogym.project.user.oauth2Account;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KaKaoOauth2 implements SocialOauth2 {

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri-code}")
    private String redirectUriCode;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    @Override
    public String getRedirectUrlCode() {
        Map<String, Object> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", clientId);
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
        params.set("redirect_uri", redirectUriCode);
        params.set("code", code);

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
            userAttributes.put("provider", "kakao");
            userAttributes.put("providerId", responseEntity.getBody().get("id").toString());
            Map<String, Object> kakaoAccount = (Map) responseEntity.getBody().get("kakao_account");
            userAttributes.put("email", kakaoAccount.get("email"));
            Map<String, Object> properties = (Map) responseEntity.getBody().get("properties");
            userAttributes.put("name", properties.get("nickname"));

            return userAttributes;
        }

        return new HashMap<>();
    }
}