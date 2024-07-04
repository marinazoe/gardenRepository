package com.example.gardeningPlanner.controller;

import static com.example.gardeningPlanner.SecurityMockMvc.HttpMethod.GET;
import static com.example.gardeningPlanner.SecurityMockMvc.HttpMethod.POST;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.gardeningPlanner.SecurityMockMvc;
import com.example.gardeningPlanner.Repositories.IPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.Plant;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.authentication.UserAccountDetails;

@WebMvcTest(PlantListController.class)
public class PlantListControllerTest extends SecurityMockMvc {

    @MockBean
    private IPlantRepository plantRepository;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IUserPlantRepository userPlantRepository;

    private Plant monstera;
    private UserAccount mockUserAccount;
    private UserAccountDetails mockUserAccountDetails;

    @BeforeEach
    public void setUp() {
        
        monstera = new Plant("Monstera deliciosa", 
                        4, 
                        2,
                        2, 
                        1, 
                        "Heller Standort, keine direkte Sonne", 
                        18, 
                        60);
        when(plantRepository.findById(monstera.getId())).thenReturn(Optional.of(monstera));

        
        mockUserAccount = new UserAccount("testUser", "password", "test@example.com");
        mockUserAccountDetails = new UserAccountDetails(mockUserAccount);

        when(userRepository.findById(mockUserAccount.getId())).thenReturn(Optional.of(mockUserAccount));
    }

    @Test
    @WithMockUser(username="testUser", roles={"USER"})
    public void testViewPlantList() throws Exception {

        // Act & Assert
        mvc.perform(request(GET, "/pflanzenListe"))
            .andExpect(status().isOk())
            .andExpect(view().name("plant_list"));
    }

    @Test
    @WithMockUser(username="testUser", roles={"USER"})
    public void testAddPlantToUser() throws Exception {

        // Act & Assert
        mvc.perform(request(POST, "/pflanzeHinzufuegen")
                .param("plantId", String.valueOf(monstera.getId()))
                .with(user(mockUserAccountDetails)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pflanzenListe"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testViewPlantListWithExistingPlant() throws Exception {
        
        // Arrange
        when(plantRepository.findAll()).thenReturn(Arrays.asList(monstera));

        // Arrange & Assert
        mvc.perform(request(GET, "/pflanzenListe"))
            .andExpect(status().isOk())
            .andExpect(view().name("plant_list"))
            .andExpect(model().attribute("list", hasSize(1)))
            .andExpect(model().attribute("list", hasItem(hasProperty("name", is("Monstera deliciosa")))))
            .andExpect(model().attribute("list", hasItem(hasProperty("water_summer", is(4)))))
            .andExpect(model().attribute("list", hasItem(hasProperty("water_winter", is(2)))))
            .andExpect(model().attribute("list", hasItem(hasProperty("fertilize_summer", is(2)))))
            .andExpect(model().attribute("list", hasItem(hasProperty("fertilize_winter", is(1)))))
            .andExpect(model().attribute("list", hasItem(hasProperty("spot", is("Heller Standort, keine direkte Sonne")))))
            .andExpect(model().attribute("list", hasItem(hasProperty("temperature", is(18.0F)))))
            .andExpect(model().attribute("list", hasItem(hasProperty("humidity", is(60)))));
    }
}


