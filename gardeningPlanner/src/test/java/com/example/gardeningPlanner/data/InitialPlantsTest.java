package com.example.gardeningPlanner.data;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.gardeningPlanner.Tables.Plant;

class InitialPlantsTest {

    @Test
    void testGetInitialPlants() {
        // Arrange and Act
        List<Plant> testPlantList = InitialPlants.getInitialPlants();

        // Assert
        assertThat(testPlantList).isNotEmpty();
        assertThat(testPlantList).hasSize(17);
    }

    @Test
    void testGetInitialPlantsAndTestSomePlants() {
        // Arrange and Act
        List<Plant> testPlantList = InitialPlants.getInitialPlants();
        Plant testPlant1 = testPlantList.get(0);
        Plant testPlant2 = testPlantList.get(16);

        // Assert
        assertThat(testPlant1).isNotNull();
        assertThat(testPlant1.getName()).isEqualTo("Monstera deliciosa");
        assertThat(testPlant1.getWater_summer()).isEqualTo(4);
        assertThat(testPlant1.getWater_winter()).isEqualTo(2);
        assertThat(testPlant1.getFertilize_summer()).isEqualTo(2);
        assertThat(testPlant1.getFertilize_winter()).isEqualTo(1);
        assertThat(testPlant1.getSpot()).isEqualTo("Heller Standort, keine direkte Sonne");
        assertThat(testPlant1.getTemperature()).isEqualTo(18);
        assertThat(testPlant1.getHumidity()).isEqualTo(60);

        assertThat(testPlant2).isNotNull();
        assertThat(testPlant2.getName()).isEqualTo("Minze");
        assertThat(testPlant2.getWater_summer()).isEqualTo(4);
        assertThat(testPlant2.getWater_winter()).isEqualTo(2);
        assertThat(testPlant2.getFertilize_summer()).isEqualTo(2);
        assertThat(testPlant2.getFertilize_winter()).isEqualTo(1);
        assertThat(testPlant2.getSpot()).isEqualTo("Halbschatten - Sonne");
        assertThat(testPlant2.getTemperature()).isEqualTo(18);
        assertThat(testPlant2.getHumidity()).isEqualTo(60);

    }
}
