package com.ogym.project.user.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByLoginId(String loginId);
    Optional<SiteUser> findByEmail(String email);
    Optional<SiteUser> findByNickname(String nickname);
    Optional<SiteUser> findByPhoneNumber(String phoneNumber);
    Optional<SiteUser> findByUsername(String username);
    SiteUser findByLoginIdAndEmail(String loginId, String email);
}

