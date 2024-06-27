package com.example.gardeningPlanner.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;

import java.util.List;
import java.util.Optional;

public interface IUserPlantRepository extends ListCrudRepository<UserPlant, Integer> {

    Optional<List<UserPlant>> findAllByUserAccount(UserAccount userAccount);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User_Plant SET nickname = ?1 WHERE id = ?2", nativeQuery = true)
    void updateNicknameById(String nickname, int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User_Plant SET notes = ?1 WHERE id = ?2", nativeQuery = true)
    void updateNotesById(String notes, int id);
}
