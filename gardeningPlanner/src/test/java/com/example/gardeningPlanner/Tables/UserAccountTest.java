package com.example.gardeningPlanner.Tables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class UserAccountTest {

    @Test
    void testCreateUserAccountThatShouldHaveAuthorities() {
        // Arrange & Act
        var testAccount = new UserAccount("testUser", "testPassword", "test@mail.test");

        // Assert
        var authorities = testAccount.getAuthorities();
        assertEquals(1, authorities.size()); // Included the default "ROLE_USER"
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
    
    @Test
    void testAddAuthorities() {
        // Arrange
        var testAccount = new UserAccount("testUser", "testPassword", "test@mail.test");

        // Act
        testAccount.addAuthorities("ROLE_ADMIN", "ROLE_VIP");

        // Assert
        var authorities = testAccount.getAuthorities();
        assertEquals(3, authorities.size()); // Included the default "ROLE_USER"
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_VIP")));
    }

    @Test
    void testGetterAndSetters() {
        // Arrange
        UserAccount testAccount = new UserAccount();
        testAccount.setId(1);
        testAccount.setUsername("testUser");
        testAccount.setPasswordHash("testPassword");
        testAccount.setEmail("test@mail.test");
        testAccount.addAuthorities("ROLE_USER");

        Plant testPlant = new Plant("testPlant", 1, 2, 3, 4, "testSpot", 1.0f, 5);

        UserPlant testUserPlant = new UserPlant(testPlant, testAccount);

        testAccount.setUserPlants(List.of(testUserPlant));

        // Act
        int testId = testAccount.getId();
        String testUsername = testAccount.getUsername();
        String testPassword = testAccount.getPasswordHash();
        String testMail = testAccount.getEmail();
        var testAuthorities = testAccount.getAuthorities();
        var testUserplantList = testAccount.getUserPlants();

        // Assert
        assertEquals(1, testId);
        assertEquals("testUser", testUsername);
        assertEquals("testPassword", testPassword);
        assertEquals("test@mail.test", testMail);
        assertTrue(testAuthorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(testUserplantList.get(0).getPlant().getName().contains("testPlant"));
    }

}
