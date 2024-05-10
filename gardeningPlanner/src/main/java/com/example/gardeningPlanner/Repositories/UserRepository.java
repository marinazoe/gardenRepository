package com.example.gardeningPlanner.Repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.example.gardeningPlanner.Tables.UserAccount;

public interface UserRepository extends ListCrudRepository<UserAccount, Integer> {

}
