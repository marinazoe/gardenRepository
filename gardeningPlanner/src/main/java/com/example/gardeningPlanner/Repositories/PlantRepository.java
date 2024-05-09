package com.example.gardeningPlanner.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.gardeningPlanner.Tables.Plant;

public interface PlantRepository extends CrudRepository<Plant, Integer> {

}
