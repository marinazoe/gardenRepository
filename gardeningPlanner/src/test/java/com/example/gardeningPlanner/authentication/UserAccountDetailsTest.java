package com.example.gardeningPlanner.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.gardeningPlanner.Tables.UserAccount;

public class UserAccountDetailsTest {
    
    private UserAccount userAccount;
    private UserAccountDetails userAccountDetails;

    @BeforeEach
    void setup() {
        // Arrange
        userAccount = new UserAccount("testUser", "testPassword", "test@mail.test");
        userAccount.setId(1);
        userAccountDetails = new UserAccountDetails(userAccount);
    }

    @Test
    void testGetId() {
        // Act
        var userId = userAccountDetails.getId();

        // Assert
        assertEquals(1, userId);
    }

    @Test
    void testGetEmail() {
        // Act
        var userEmail = userAccountDetails.getEmail();

        // Assert
        assertEquals("test@mail.test", userEmail);
    }

    @Test
    void testGetUsername() {
        // Act
        var username = userAccountDetails.getUsername();

        // Assert
        assertEquals("testUser", username);
    }

    @Test
    void testGetAuthorities() {
        // Act
        var userAuthorities = userAccountDetails.getAuthorities();

        // Assert
        assertEquals(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")),  userAuthorities);
    }

    @Test
    void testGetPassword() {
        // Act
        var userPassword = userAccountDetails.getPassword();

        // Assert
        assertEquals("testPassword", userPassword);
    }

    @Test
    void testisAccountNonExpired() {
        // Act
        var userAccountNonExpired = userAccountDetails.isAccountNonExpired();

        // Assert
        assertEquals(true, userAccountNonExpired);
    }

    @Test
    void testIsAccountNonLocked() {
        // Act
        var userAccountNonLocked = userAccountDetails.isAccountNonLocked();

        // Assert
        assertEquals(true, userAccountNonLocked);
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Act
        var userCredentialsNonExpired = userAccountDetails.isCredentialsNonExpired();

        // Assert
        assertEquals(true, userCredentialsNonExpired);
    }

    @Test
    void testIsEnabled() {
        // Act
        var userIsEnabled = userAccountDetails.isEnabled();

        // Assert
        assertEquals(true, userIsEnabled);
    }

    @Test
    void testEraseCredentials() {
        // Act
        userAccountDetails.eraseCredentials();
        var userPassword = userAccountDetails.getPassword();

        // Assert
        assertEquals(null, userPassword);
    }

}
