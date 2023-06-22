package com.ogym.project.user.oauth2;

import com.ogym.project.user.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    SocialAccount findByProviderAndProviderId(String provider, String providerId);

    Optional<SocialAccount> findByLoginId(String loginId);
}