package by.konovalchik.domesticservice.entity;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_EXECUTOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
