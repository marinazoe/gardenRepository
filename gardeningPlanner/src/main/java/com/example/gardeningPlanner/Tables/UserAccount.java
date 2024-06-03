package com.example.gardeningPlanner.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 64, nullable = true, unique = false)
    private String email_address;

    @Column(length = 256, nullable = false, unique = false)
    private String password_hash;

    @Column(length = 64, nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private List<UserPlant> userPlants;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities = new ArrayList<>();

    // ------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------
    protected UserAccount() {
    }

    public UserAccount(String username, String password_hash, String email_address) {
        this.username = username;
        this.password_hash = password_hash;
        this.email_address = email_address;
        addAuthorities("ROLE_USER");
    }

    public UserAccount(String username, String password_hash) {
        this.username = username;
        this.password_hash = password_hash;
        addAuthorities("ROLE_USER");
    }

    // ------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------

    public void addAuthorities(String... authorities) {
        this.authorities.addAll(convertToGrantedAuthorities(authorities));
    }

    private static List<SimpleGrantedAuthority> convertToGrantedAuthorities(String... authorities) {
        return Arrays.stream(authorities).map(SimpleGrantedAuthority::new).toList();
    }

    // ------------------------------------------------------------
    // Getters
    // ------------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email_address;
    }

    public String getPasswordHash() {
        return password_hash; // vorläufig
    }

    public String getUsername() {
        return username;
    }

    public List<UserPlant> getUserPlants() {
        return userPlants;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    // ------------------------------------------------------------
    // Setters
    // ------------------------------------------------------------
    public void setId(int _id) {
        id = _id;
    }

    public void setEmail(String _email) {
        email_address = _email;
    }

    public void setPasswordHash(String _password) {
        password_hash = _password; // vorläufig
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public void setUserPlants(List<UserPlant> _userPlants) {
        userPlants = _userPlants;
    }
}
