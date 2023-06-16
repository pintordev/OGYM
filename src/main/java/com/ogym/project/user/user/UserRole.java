package com.ogym.project.user.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    COMMUNITY_MANAGER("community manager"),
    TRAINER("trainer"),
    SELLER("seller"),
    USER("user");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
