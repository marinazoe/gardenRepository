package com.example.gardeningPlanner.config;

import java.util.NoSuchElementException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gardeningPlanner.Repositories.IPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;


/*
 * Has to be removed before Production!
 */
@Configuration
public class DefaultTableValue {

    private IUserRepository iUserRepository;
    private IUserPlantRepository iUserPlantRepository;
    private IPlantRepository iPlantRepository;
    private PasswordEncoder passwordEncoder;

    DefaultTableValue(IUserRepository iUserRepository, IUserPlantRepository iUserPlantRepository, IPlantRepository iPlantRepository, PasswordEncoder passwordEncoder){
        this.iUserRepository = iUserRepository;
        this.iUserPlantRepository = iUserPlantRepository;
        this.iPlantRepository = iPlantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Order(2)
    @Bean
	CommandLineRunner commandLineRunnerWithDefaultTableValues (IUserRepository iUserRepository, IUserPlantRepository iUserPlantRepository, IPlantRepository iPlantRepository){
		return args -> {
            insertDefaultValues("default@mail.com", "default", "default", "Monstera deliciosa");
		};
	}

    private boolean isNotInUserTable(String name) {
        return !iUserRepository.findByUsername(name).isPresent();
    }

    private void insertDefaultValues(String email, String username, String password, String name){
        if(isNotInUserTable(username)){
            var newDefaultPlant = iPlantRepository.findByName(name).orElseThrow(NoSuchElementException::new);
            var newDefaultUser = new UserAccount(username, passwordEncoder.encode(password), email);
            iUserRepository.save(newDefaultUser);
            var newDefaultUserPlant = new UserPlant(newDefaultPlant ,newDefaultUser);
            iUserPlantRepository.save(newDefaultUserPlant);
        }
    }
}
