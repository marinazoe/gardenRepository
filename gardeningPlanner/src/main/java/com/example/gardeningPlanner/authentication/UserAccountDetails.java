package com.example.gardeningPlanner.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;

// Data for UserDetailsService to keep current logged in user

public final class UserAccountDetails implements UserDetails, CredentialsContainer{
    private int id;
    private String emailAddress;
    private String passwordHash;
    private String username;
    private List<UserPlant> userPlants;
    private List<GrantedAuthority> authorities;

    public UserAccountDetails(UserAccount userAccount) {
        this.id = userAccount.getId();
        this.emailAddress = userAccount.getEmail();
        this.passwordHash = userAccount.getPasswordHash();
        this.username = userAccount.getUsername();
        this.authorities = new ArrayList<>(userAccount.getAuthorities());
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return emailAddress;
    }

    public List<UserPlant> getUserPlants() {
        return userPlants;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
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

    @Override
    public void eraseCredentials() {
        passwordHash = null;
        emailAddress = null;
    }
}
