package com.example.gardeningPlanner.authentication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gardeningPlanner.Repositories.IUserRepository;

// Based on the Unit Tests from "Programmieren 2"

public class AuthentificationConfigTest {

    @Test
    void testAuthenticationManagerBeanShouldBeDefined() {
        // Arrange and Act
        var authenticationManager = new AuthentificationConfig().authenticationManager(mock(UserDetailsService.class), mock(PasswordEncoder.class));

        // Assert
        assertNotNull(authenticationManager);
    }

    @Test
    void testPasswordEncoderBeanShouldBeDefined() {
        // Arrange and Act
        var passwordEncoder = new AuthentificationConfig().passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder.matches("test", passwordEncoder.encode("test")));

    }

    @Test
    void testUserDetailsServiceBeanShouldBeDefined() {
        // Arrange and Act
        var userDetailsService = new AuthentificationConfig().userDetailsService(mock(IUserRepository.class));

        // Assert
        assertNotNull(userDetailsService);
    }
}
