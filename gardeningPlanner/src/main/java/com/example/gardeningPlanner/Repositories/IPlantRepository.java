package com.example.gardeningPlanner.Repositories;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.example.gardeningPlanner.Tables.Plant;

public interface IPlantRepository extends ListCrudRepository<Plant, Integer> {

    Optional<Plant> findByName(String name);

}
