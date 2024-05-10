package com.example.gardeningPlanner.Repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.example.gardeningPlanner.Tables.Plant;

public interface IPlantRepository extends ListCrudRepository<Plant, Integer> {

}
