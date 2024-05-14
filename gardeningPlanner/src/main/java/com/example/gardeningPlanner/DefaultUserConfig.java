package com.example.gardeningPlanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;


/*
 * Has to be removed before Production!
 */
@Configuration
public class DefaultUserConfig {
    
    private IUserRepository iUserRepository;
    private PasswordEncoder passwordEncoder;

    DefaultUserConfig(IUserRepository iUserRepository, PasswordEncoder passwordEncoder){
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
	CommandLineRunner commandLineRunner(IUserRepository iUserRepository){
		return args -> {
            insertUserAccount("default@mail.com", "DefaultName", "defaultpasswort");
		};
	}

    private boolean isNotInDatabase(String name) {
        return !iUserRepository.findByUsername(name).isPresent();
    }

    private void insertUserAccount(String email, String name, String password){
        if(isNotInDatabase(name)){
            var newDefaultUser = new UserAccount(email, name, passwordEncoder.encode(password));
            iUserRepository.save(newDefaultUser);
        }
    }
}
