package com.ogym.project.user.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private final Map<String, Object> attributes;
    private final User user;
    private final SocialAccount socialAccount;
    private final SocialAccountRepository socialAccountRepository;

    public static PrincipalDetails create(UserDetails userDetails, Map<String, Object> attributes, SocialAccountRepository socialAccountRepository) {
        return new PrincipalDetails((User) userDetails, attributes, socialAccountRepository);
    }

    public PrincipalDetails(User user, Map<String, Object> attributes, SocialAccountRepository socialAccountRepository) {
        this.user = user;
        this.attributes = attributes;
        this.socialAccount = getSocialAccountFromAttributes(attributes, socialAccountRepository);
        this.socialAccountRepository = socialAccountRepository;
    }

    public PrincipalDetails(User user) {
        this.user = user;
        this.attributes = null;
        this.socialAccount = null;
        this.socialAccountRepository = null;
    }

    private SocialAccount getSocialAccountFromAttributes(Map<String, Object> attributes, SocialAccountRepository socialAccountRepository) {
        if (attributes.containsKey("provider") && attributes.containsKey("providerId")) {
            String provider = attributes.get("provider").toString();
            String providerId = attributes.get("providerId").toString();
            // Check if social account exists based on provider and providerId
            SocialAccount socialAccount = socialAccountRepository.findByProviderAndProviderId(provider, providerId);
            return socialAccount;
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> user.getUsername());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}