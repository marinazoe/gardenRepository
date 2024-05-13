package com.example.gardeningPlanner.Repositories;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.example.gardeningPlanner.Tables.UserAccount;

public interface IUserRepository extends ListCrudRepository<UserAccount, Integer> {

    Optional<UserAccount> findByUsername(String username);
}
