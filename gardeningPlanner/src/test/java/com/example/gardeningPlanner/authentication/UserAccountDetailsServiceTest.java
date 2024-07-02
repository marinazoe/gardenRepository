package com.example.gardeningPlanner.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;

class UserAccountDetailsServiceTest {

    @Test
    void testLoadUserByUsername() {
        // Arrange
        var iUserRepository = mock(IUserRepository.class);
        var userDetailsService = new UserAccountDetailsService((iUserRepository));

        var userAccount = new UserAccount("testUser", "testPassword", "test@mail.test");
        userAccount.setId(1);
        when(iUserRepository.findByUsername("testUser")).thenReturn(Optional.of(userAccount));

        // Act
        var userDetails = (UserAccountDetails) userDetailsService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals(1, userDetails.getId());
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertEquals("test@mail.test", userDetails.getEmail());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));       

    }

    @Test
    void testLoadUserByUsernameWithUnknownUser() {
            // Arrange
            var iUserRepository = mock(IUserRepository.class);
            var userDetailsService = new UserAccountDetailsService((iUserRepository));

            when(iUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());

            // Act and Assert
            assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("unknownTestUser"));

    }
}
