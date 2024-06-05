package com.example.gardeningPlanner.Repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;

import java.util.List;
import java.util.Optional;

public interface IUserPlantRepository extends ListCrudRepository<UserPlant, Integer> {

    Optional<List<UserPlant>> findAllByUserAccount(UserAccount userAccount);

}
