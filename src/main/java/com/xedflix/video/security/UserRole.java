package com.xedflix.video.security;

public enum UserRole {
    XEDFLIX_SUPER_ADMIN("XEDFLIX_SUPER_ADMIN"),
    ORG_SUPER_ADMIN("ORG_SUPER_ADMIN"),
    ORG_ADMIN("ORG_ADMIN"),
    ORG_USER("ORG_USER"),
    END_USER("END_USER");

    private String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return this.userRole;
    }
}
