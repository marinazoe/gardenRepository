package com.example.gardeningPlanner.Repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.example.gardeningPlanner.Tables.UserPlant;

public interface IUserPlantRepository extends ListCrudRepository<UserPlant, Integer> {

}
