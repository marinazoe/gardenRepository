package com.example.gardeningPlanner.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.example.gardeningPlanner.Repositories.IPlantRepository;
import com.example.gardeningPlanner.Tables.Plant;
import com.example.gardeningPlanner.data.InitialPlants;

@Order(1)
@Configuration
public class PlantInitializer {
    
    private final IPlantRepository iPlantRepository;

    PlantInitializer(IPlantRepository iPlantRepository) {
        this.iPlantRepository = iPlantRepository;
    }
    
    @Bean
    CommandLineRunner commandLineRunner(IPlantRepository iPlantRepository) {
        return args -> {
            insertInitialPlants();
        };
    }

    private boolean isNotInPlantTable(String name) {
        return !iPlantRepository.findByName(name).isPresent();
    }

    private void insertInitialPlants() {
        List<Plant> plants = InitialPlants.getInitialPlants();
        for (Plant plant : plants) {
            if(isNotInPlantTable(plant.getName())) {
                iPlantRepository.save(plant);
            }
        }
    }
}
