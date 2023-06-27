package com.ogym.project.user.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum UserRole {

    ADMIN("admin"),
    COMMUNITY_MANAGER("community manager"),
    TRAINER("trainer"),
    SELLER("seller"),
    USER("user");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
