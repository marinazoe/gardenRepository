package com.example.gardeningPlanner.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gardeningPlanner.Repositories.IPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.Plant;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;

@ExtendWith(MockitoExtension.class)
public class DefaultTableValueTest {

    @Mock
    private IUserRepository iUserRepository;

    @Mock
    private IUserPlantRepository iUserPlantRepository;

    @Mock
    private IPlantRepository iPlantRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DefaultTableValue defaultTableValue;

    @BeforeEach
    public void setUp() {
        defaultTableValue = new DefaultTableValue(iUserRepository, iUserPlantRepository, iPlantRepository, passwordEncoder);
    }

    @Test
    public void testCommandLineRunnerWithDefaultTableValues() throws Exception {
        // Arrange

        when(iUserRepository.findByUsername("default")).thenReturn(Optional.empty());
        when(iPlantRepository.findByName("Monstera deliciosa")).thenReturn(Optional.of(new Plant("Monstera deliciosa", 
                                                                                            4, 
                                                                                            2,
                                                                                            2, 
                                                                                            1, 
                                                                                            "Heller Standort, keine direkte Sonne", 
                                                                                            18,
                                                                                            60)));

        // Act
        var commandLineRunner = defaultTableValue.commandLineRunnerWithDefaultTableValues();
        commandLineRunner.run(new String[] {});

        // Assert
        verify(iUserRepository).save(any(UserAccount.class));
        verify(iUserPlantRepository).save(any(UserPlant.class));
    }
    
    @Test
    public void testCommandLineRunnerWithDefaultTableValuesWhenUserExistsShouldNotInsertDefaultValues() throws Exception {
        // Arrange
        when(iUserRepository.findByUsername("default")).thenReturn(Optional.of(new UserAccount("default", "default", "default@mail.com")));

        // Act
        var commandLineRunner = defaultTableValue.commandLineRunnerWithDefaultTableValues();
        commandLineRunner.run(new String[] {});

        // Assert
        verify(iUserRepository, never()).save(any(UserAccount.class));
        verify(iUserPlantRepository, never()).save(any(UserPlant.class));
    }
    
}

