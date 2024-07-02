package com.example.gardeningPlanner.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.gardeningPlanner.Tables.UserAccount;

@DataJpaTest
public class IUserRepositoryTest {

    @Autowired
    IUserRepository iUserRepository;

    @Test
    void testFindByName() {
        // Arrange
        var testAccount = new UserAccount("testUser", "testPassword", "test@email.test");
        iUserRepository.save(testAccount);

        // Act
        var result = iUserRepository.findByUsername("testUser");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testUser");
    }

    @Test
    void testFindByNameWithNonExistingUser() {
        // Arrange
        var username = "testUser";

        // Act
        var result = iUserRepository.findByUsername(username);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testFindByNameWithNull() {
        // Act
        var result = iUserRepository.findByUsername(null);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testFindById() {
        // Arrange
        var testAccount = new UserAccount("testUser", "testPassword", "test@email.test");
        var dbUser = iUserRepository.save(testAccount);

        // Act
        var result = iUserRepository.findById(dbUser.getId());

        // Assert
        assertThat(result).isPresent();
    }

    @Test
    void testFindByIdWithNonExistingUser() {
        // Arrange
        var testId = 5;

        // Act
        var result = iUserRepository.findById(testId);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testDeleteById() {
        // Arrange
        var testAccount = new UserAccount("testUserDeletion", "testPassword", "test@email.test");
        var dbUser = iUserRepository.save(testAccount).getId();

        // Act
        iUserRepository.deleteById(dbUser);
        var result = iUserRepository.findByUsername("testUserDeletion");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testDeleteByIdWithNull() {
        // Act and Assert
        assertThrows(Exception.class, () -> iUserRepository.deleteById(null));
    }

}
