package com.duryskuba.hotelproject.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Basic;
import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private final BasicPerson basicPerson;

    public UserPrincipal(final BasicPerson basicPerson) {
        this.basicPerson = basicPerson;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return basicPerson.getPassword();
    }

    @Override
    public String getUsername() {
        return basicPerson.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return basicPerson.getId();
    }
}
