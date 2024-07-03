package com.example.gardeningPlanner.Tables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PlantTest {
    
    @Test
    void testGetterAndSetters() {
        // Arrange
        Plant testPlant = new Plant();
        testPlant.setId(1);
        testPlant.setName("testName");
        testPlant.setWater_summer(2);
        testPlant.setWater_winter(3);
        testPlant.setFertilize_summer(2);
        testPlant.setFertilize_winter(4);
        testPlant.setSpot("testSpot");
        testPlant.setTemperature(30.0f);
        testPlant.setHumidity(20);

        // Act
        int testId = testPlant.getId();
        String testName = testPlant.getName();
        int testWater_summer = testPlant.getWater_summer();
        int testWater_winter = testPlant.getWater_winter();
        int testFertilize_summer = testPlant.getFertilize_summer();
        int testFertilize_winter = testPlant.getFertilize_winter();
        String testSpot = testPlant.getSpot();
        float testTemperature = testPlant.getTemperature();
        int testHumidity = testPlant.getHumidity();

        // Assert
        assertEquals(1, testId);
        assertEquals("testName", testName);
        assertEquals(2, testWater_summer);
        assertEquals(3, testWater_winter);
        assertEquals(2, testFertilize_summer);
        assertEquals(4, testFertilize_winter);
        assertEquals("testSpot", testSpot);
        assertEquals(30.0f, testTemperature);
        assertEquals(20, testHumidity);
    }
}
