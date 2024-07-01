package com.example.gardeningPlanner.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.gardeningPlanner.Repositories.IPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.Plant;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;

@WebMvcTest(PlantListController.class)
public class PlantListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPlantRepository plantRepository;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IUserPlantRepository userPlantRepository;

    private List<Plant> plants;

    @BeforeEach
    void setup() {
        Plant monstera = new Plant("Monstera", 1, 1, 1, 1, "Low light", 20, 50);
        plants = Arrays.asList(monstera);
        when(plantRepository.findAll()).thenReturn(plants);
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testViewPlantList() throws Exception {
        mockMvc.perform(get("/pflanzenListe"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("list", plants))
                .andExpect(view().name("plant_list"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testAddPlantToUser() throws Exception {
        when(plantRepository.findById(1)).thenReturn(Optional.of(plants.get(0)));
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserAccount("user", "pass", "user@example.com")));

        mockMvc.perform(post("/pflanzeHinzufuegen")
                .param("plantId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pflanzenListe"));

        verify(userPlantRepository).save(any(UserPlant.class));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testAddNonExistentPlant() throws Exception {
        when(plantRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(post("/pflanzeHinzufuegen")
                .param("plantId", "999"))
                .andExpect(status().isNotFound());
    }
}


