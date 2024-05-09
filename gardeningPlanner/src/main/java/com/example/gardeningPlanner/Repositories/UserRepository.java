package com.example.gardeningPlanner.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.gardeningPlanner.Tables.UserAccount;

public interface UserRepository extends CrudRepository<UserAccount, Integer> {

}
