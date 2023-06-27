package com.ogym.project.user.oauth2Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Oauth2AccountRepository extends JpaRepository<Oauth2Account, Long> {
    Optional<Oauth2Account> findByProviderAndProviderId(String provider, String providerId);
}