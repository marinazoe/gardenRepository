package com.example.gardeningPlanner.Tables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class UserPlantTest {

    @Test
    void testGetterAndSetters() {
        // Arrange
        UserPlant testUserPlant = new UserPlant();
        testUserPlant.setId(1);
        testUserPlant.setNickname("testNickname");
        testUserPlant.setNotes("testNote");
        testUserPlant.setAdded_date(LocalDate.now());
        testUserPlant.setNotifications_enabled(false);

        Plant testPlant = new Plant("testPlant", 1, 2, 3, 4, "testSpot", 1.0f, 5);
        UserAccount testAccount = new UserAccount("testUser", "testPassword", "test@email.test");

        testUserPlant.setPlant(testPlant);
        testUserPlant.setUserAccount(testAccount);

        // Act
        int testId = testUserPlant.getId();
        String testNickname = testUserPlant.getNickname();
        String testNotes = testUserPlant.getNotes();
        LocalDate testDate = testUserPlant.getAdded_date();
        boolean testNotifcations_enabled = testUserPlant.getNotifications_enabled();
        Plant testUserPlantPlant = testUserPlant.getPlant();
        UserAccount testUserPlantUserAccount = testUserPlant.getUserAccount();

        // Assert
        assertEquals(1, testId);
        assertEquals("testNickname", testNickname);
        assertEquals("testNote", testNotes);
        assertEquals(LocalDate.now(), testDate);
        assertEquals(false, testNotifcations_enabled);
        assertEquals("testPlant", testUserPlantPlant.getName());
        assertEquals("testUser", testUserPlantUserAccount.getUsername());
    }
}
