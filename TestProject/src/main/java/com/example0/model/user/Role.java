package com.example0.model.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER("ROLE_USER"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_ANOMY("ROLE_ANOMY");

    @Getter
    private final String role;

    Role(String role) {
        this.role = role;
    }

    String value() {
        return role;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
