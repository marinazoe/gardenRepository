package com.example.gardeningPlanner.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.gardeningPlanner.Tables.Plant;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
class IUserPlantRepositoryTest {

    @Autowired
    IUserPlantRepository iUserPlantRepository;

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IPlantRepository iPlantRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private UserAccount testAccount;
    private UserAccount dbUser;
    private Plant testPlant;
    private UserPlant testUserPlant1;
    private UserPlant testUserPlant2;
    private UserPlant testUserPlant3;
    private UserPlant dbUserPlant;

    @BeforeEach
    private void setUp() {
        // Cleaning
        iUserPlantRepository.deleteAll();
        iUserRepository.deleteAll();
        iPlantRepository.deleteAll();

        // Arrange
        testAccount = new UserAccount("testUser", "testPassword", "test@email.test");
        dbUser = iUserRepository.save(testAccount);

        testPlant = new Plant("testPlant", 1, 2, 3, 4, "testSpot", 1.0f, 5);
        iPlantRepository.save(testPlant);

        testUserPlant1 = new UserPlant(testPlant, testAccount);
        testUserPlant2 = new UserPlant(testPlant, testAccount);
        testUserPlant3 = new UserPlant(testPlant, testAccount);
        dbUserPlant = iUserPlantRepository.save(testUserPlant1);
        iUserPlantRepository.saveAll(List.of(testUserPlant2, testUserPlant3));
    }

    @Test
    void testFindById() {
        // Act
        var result = iUserPlantRepository.findById(dbUserPlant.getId());

        // Assert
        assertThat(result).isPresent();
    }

    @Test
    void testFindByIdWithNonExistingUserPlant() {
        // Arrange
        var testId = 12;

        // Act
        var result = iUserPlantRepository.findById(testId);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testDeleteById() {
        // Act
        iUserPlantRepository.deleteById(dbUserPlant.getId());
        var result = iUserPlantRepository.findById(dbUserPlant.getId());

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testDeleteByIdWithNull() {
        // Act and Assert
        assertThrows(Exception.class, () -> iUserPlantRepository.deleteById(null));
    }

    @Test
    void testFindAllByUserAccount() {
        // Act
        var result = iUserPlantRepository.findAllByUserAccount(dbUser);

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.get().get(0).getPlant().getName()).isEqualTo("testPlant");
        assertThat(result.get().get(1).getPlant().getName()).isEqualTo("testPlant");
        assertThat(result.get().get(2).getPlant().getName()).isEqualTo("testPlant");
    }

    @Test
    void testFindAllByUserAccountWithNonExistingUserPlants() {
        // Arrange
        iUserPlantRepository.deleteAll();

        // Act
        var result = iUserPlantRepository.findAllByUserAccount(dbUser);

        // Assert
        assertThat(result.get()).isEmpty();
    }

    @Test
    void testFindAllByUserAccountWithNull() {
        // Act
        var result = iUserPlantRepository.findAllByUserAccount(null);

        // Assert
        assertThat(result.get()).isEmpty();
    }

    @Test
    void testUpdateNicknameById() {
        // Act
        iUserPlantRepository.updateNicknameById("testNickname", dbUserPlant.getId());

        // To ensure that changes are made
        entityManager.flush();
        entityManager.clear();

        UserPlant updatedUserPlant = iUserPlantRepository.findById(dbUserPlant.getId()).orElseThrow();

        // Assert
        assertThat(updatedUserPlant.getNickname()).isEqualTo("testNickname");
    }

    @Test
    void testUpdateNicknameByIdWithWrongId() {
        // Act
        iUserPlantRepository.updateNicknameById("testNickname", 10);

        // To ensure that changes are made
        entityManager.flush();
        entityManager.clear();

        UserPlant updatedUserPlant = iUserPlantRepository.findById(dbUserPlant.getId()).orElseThrow();

        // Assert
        assertThat(updatedUserPlant.getNickname()).isEqualTo(null);
    }

    @Test
    void testUpdateNicknameByIdWithNullNickname() {
        // Act
        iUserPlantRepository.updateNicknameById(null, dbUserPlant.getId());

        // To ensure that changes are made
        entityManager.flush();
        entityManager.clear();

        UserPlant updatedUserPlant = iUserPlantRepository.findById(dbUserPlant.getId()).orElseThrow();

        // Assert
        assertThat(updatedUserPlant.getNickname()).isEqualTo(null);
    }

    @Test
    void testUpdateNotesById() {
        // Act
        iUserPlantRepository.updateNotesById("testNote", dbUserPlant.getId());

        // To ensure that changes are made
        entityManager.flush();
        entityManager.clear();

        UserPlant updatedUserPlant = iUserPlantRepository.findById(dbUserPlant.getId()).orElseThrow();

        // Assert
        assertThat(updatedUserPlant.getNotes()).isEqualTo("testNote");
    }

    @Test
    void testUpdateNotesByIdWithWrongId() {
        // Act
        iUserPlantRepository.updateNotesById("testNote", 20);

        // To ensure that changes are made
        entityManager.flush();
        entityManager.clear();

        UserPlant updatedUserPlant = iUserPlantRepository.findById(dbUserPlant.getId()).orElseThrow();

        // Assert
        assertThat(updatedUserPlant.getNotes()).isEqualTo(null);
    }

    @Test
    void testUpdateNotesByIdWithNullNote() {
        // Act
        iUserPlantRepository.updateNotesById(null, dbUserPlant.getId());

        // To ensure that changes are made
        entityManager.flush();
        entityManager.clear();

        UserPlant updatedUserPlant = iUserPlantRepository.findById(dbUserPlant.getId()).orElseThrow();

        // Assert
        assertThat(updatedUserPlant.getNotes()).isEqualTo(null);
    }
}
