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
public class GoogleOauth2 implements SocialOauth2 {

    @Value("${custom.google.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${custom.google.redirect-uri-code}")
    private String redirectUriCode;

    @Value("${custom.google.token-uri}")
    private String tokenUri;

    @Value("${custom.google.user-info-uri}")
    private String userInfoUri;

    @Override
    public String getRedirectUrlCode() {

        Map<String, Object> params = new HashMap<>();
        params.put("scope", "email profile");
        params.put("access_type", "offline");
        params.put("include_granted_scopes", "true");
        params.put("response_type", "code");
        params.put("state", "state_parameter_passthrough_value");
        params.put("redirect_uri", redirectUriCode);
        params.put("client_id", clientId);

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
        params.set("code", code);
        params.set("client_id", clientId);
        params.set("client_secret", clientSecret);
        params.set("redirect_uri", redirectUriCode);
        params.set("grant_type", "authorization_code");

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

        System.out.println("들어옴");

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(userInfoUri, HttpMethod.GET, restRequest, JSONObject.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userAttributes = new HashMap<>();
            userAttributes.put("provider", "google");
            userAttributes.put("providerId", responseEntity.getBody().get("id"));
            userAttributes.put("email", responseEntity.getBody().get("email"));
            userAttributes.put("name", responseEntity.getBody().get("name"));

            return userAttributes;
        }

        return new HashMap<>();
    }
}
