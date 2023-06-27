package com.ogym.project.user.user;

import com.ogym.project.user.oauth2Account.Oauth2Account;
import groovy.transform.builder.Builder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserRole authority;

    @Column(unique = true)
    private String loginId;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String username;

    private String birthDate;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String address;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime lastLoginDate;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Oauth2Account> socialAccount;
}
