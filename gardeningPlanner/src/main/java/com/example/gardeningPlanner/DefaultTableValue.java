package com.example.gardeningPlanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gardeningPlanner.Repositories.IPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.Plant;
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

    @Bean
	CommandLineRunner commandLineRunner(IUserRepository iUserRepository, IUserPlantRepository iUserPlantRepository, IPlantRepository iPlantRepository){
		return args -> {
            insertDefaultValues("default@mail.com", "DefaultName", "defaultpasswort", "test");
		};
	}

    private boolean isNotInUserTable(String name) {
        return !iUserRepository.findByUsername(name).isPresent();
    }

    private boolean isNotInPlantTable(String name) {
        return !iPlantRepository.findByName(name).isPresent();
    }

    private void insertDefaultValues(String email, String username, String password, String name){
        if(isNotInUserTable(username) && isNotInPlantTable(name)){
            var newDefaultPlant = new Plant(name, 1, 2, 3, 4, "Fensterbank", 5, 6);
            iPlantRepository.save(newDefaultPlant);
            var newDefaultUser = new UserAccount(email, username, passwordEncoder.encode(password));
            iUserRepository.save(newDefaultUser);
            var newDefaultUserPlant = new UserPlant(newDefaultPlant ,newDefaultUser);
            iUserPlantRepository.save(newDefaultUserPlant);
        }
    }
}
