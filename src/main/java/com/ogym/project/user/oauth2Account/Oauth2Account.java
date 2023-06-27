package com.ogym.project.user.oauth2Account;

import com.ogym.project.user.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Oauth2Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String providerId;

    private String email;

    private String name;

    @ManyToOne
    private SiteUser parent;
}