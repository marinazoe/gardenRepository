package com.example.gardeningPlanner.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.gardeningPlanner.Repositories.IPlantRepository;
import com.example.gardeningPlanner.Tables.Plant;


@ExtendWith(MockitoExtension.class)
class PlantInitializerTest {

    @Mock
    private IPlantRepository iPlantRepository;

    @InjectMocks
    private PlantInitializer plantInitializer;

    @BeforeEach
    void setUp() {
        plantInitializer = new PlantInitializer(iPlantRepository);
    }

    @Test
    void testCommandLineRunner() throws Exception{
        // Arrange
        when(iPlantRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Act
        var commandLineRunner = plantInitializer.commandLineRunner();
        commandLineRunner.run(new String[] {});

        // Assert
        verify(iPlantRepository, times(17)).save(any(Plant.class));

    }

    @Test
    void testCommandLineRunnerWhenPlantsExistShouldNotInsertInitialPlants() throws Exception{
        // Arrange
        when(iPlantRepository.findByName(anyString())).thenReturn(Optional.of(new Plant("Monstera deliciosa", 
                                                                                4, 
                                                                                2,
                                                                                2, 
                                                                                1, 
                                                                                "Heller Standort, keine direkte Sonne", 
                                                                                18,
                                                                                60)));

        // Act
        var commandLineRunner = plantInitializer.commandLineRunner();
        commandLineRunner.run(new String[] {});

        // Assert
        verify(iPlantRepository, never()).save(any(Plant.class));

    }
}
