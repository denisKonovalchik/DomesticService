package by.konovalchik.domesticservice.entity;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    ADMIN, USER, EXECUTOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
