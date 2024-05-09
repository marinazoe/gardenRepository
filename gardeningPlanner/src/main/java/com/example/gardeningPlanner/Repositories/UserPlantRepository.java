package com.example.gardeningPlanner.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.gardeningPlanner.Tables.UserPlant;

public interface UserPlantRepository extends CrudRepository<UserPlant, Integer> {

}
