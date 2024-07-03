package com.example.gardeningPlanner.Repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.gardeningPlanner.Tables.Plant;

/*
 * IMPORTANT!
 * testFindById has different "isEqualTo()" values depending if used as a single or mutli test
 * more information down below
 */
@DataJpaTest
class IPlantRepositoryTest {

    @Autowired
    IPlantRepository iPlantRepository;

    @Test
    void testFindByName() {
        // Arrange
        var testPlant = new Plant("testPlant", 1, 2, 3, 4, "testSpot", 1.0f, 5);
        iPlantRepository.save(testPlant);

        // Act
        var result = iPlantRepository.findByName("testPlant");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("testPlant");
    }

    @Test
    void testFindByNameWithNonExistingPlant() {
        // Arrange
        var plantName = "testPlant";

        // Act
        var result = iPlantRepository.findByName(plantName);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void testFindByNameWithNull() {
        // Act
        var result = iPlantRepository.findByName(null);

        // Assert
        assertThat(result).isEmpty();
    }


    @Test
    void testFindAll() {
        // Arrange
        var testPlant1 = new Plant("testPlant1", 1, 2, 3, 4, "testSpot", 1.0f, 5);
        var testPlant2 = new Plant("testPlant2", 1, 2, 3, 4, "testSpot", 1.0f, 5);
        iPlantRepository.save(testPlant1);
        iPlantRepository.save(testPlant2);

        // Act
        var result = iPlantRepository.findAll();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo("testPlant1");
        assertThat(result.get(1).getName()).isEqualTo("testPlant2");
    }

    @Test
    void testFindAllWithNoExistingPlants() {
        // Act
        var result = iPlantRepository.findAll();

        // Assert
        assertThat(result).isEmpty();
    }


    /*
     * IMPORTANT!
     * If used as a single test: ".isEqualTo(1)" needs to be written
     * if used to test all tests: ".isEqualTo(4)" needs to be written
     */
    @Test
    void testFindById() {
        // Arrange
        var testPlant = new Plant("testPlant", 1, 2, 3, 4, "testSpot", 1.0f, 5);
        var dbTestPlant = iPlantRepository.save(testPlant);

        // Act
        var result = iPlantRepository.findById(dbTestPlant.getId());

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(4);
    }

    @Test
    void testFindByIdWithNonExistingPlant() {
        // Arrange
        var id = 2;

        // Act
        var result = iPlantRepository.findById(id);

        // Assert
        assertThat(result).isEmpty();
    }

}
