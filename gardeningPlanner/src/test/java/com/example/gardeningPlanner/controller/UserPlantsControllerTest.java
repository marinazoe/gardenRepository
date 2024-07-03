package com.example.gardeningPlanner.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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
import com.example.gardeningPlanner.authentication.UserAccountDetails;


/*
 * This test class was made with the help of Chat GPT and "portfolio-security" from "Programmieren 2"
 */
@WebMvcTest(UserPlantsController.class)
class UserPlantsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPlantRepository plantRepository;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IUserPlantRepository userPlantRepository;

    private UserAccount user;
    private UserPlant userPlant;

    @BeforeEach
    void setUp() {
        // Arrange
        user = new UserAccount("test", "test");
        user.setId(1);
        
        userPlant = new UserPlant(new Plant("testPlant", 1, 2, 3, 4, "testSpot", 1.0f, 5),user);
        userPlant.setId(1);
    }

    @Test
    @WithMockUser
    void testUserPlants() throws Exception {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userPlantRepository.findAllByUserAccount(user)).thenReturn(Optional.of(List.of(userPlant)));

        // Act and Assert
        var result = mockMvc.perform(get("/benutzerPflanzen").with(user(new UserAccountDetails(user))))
                .andExpect(status().isOk())
                .andExpect(view().name("user_plants"))
                .andExpect(model().attributeExists("userPlants"))
                .andReturn().getResponse().getContentAsString();

        assertThat(result).contains("Meine Pflanzen", "testPlant", "testSpot");     
    }

    @Test
    void testGetUserPlants_Unauthenticated() throws Exception {
        // Act and Assert
        mockMvc.perform(get("/benutzerPflanzen"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void testChangeNickname() throws Exception {
        // Arrange
        when(userPlantRepository.findById(1)).thenReturn(Optional.of(userPlant));

        // Act and Assert
        mockMvc.perform(post("/pflanzeumbennen")
                .param("userPlantId", "1")
                .param("nickname", "NewNickname")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/benutzerPflanzen"));

        verify(userPlantRepository, times(1)).updateNicknameById("NewNickname", 1);
    }

    @Test
    @WithMockUser
    void testDeletePlant() throws Exception{
        // Arrange
        when(userPlantRepository.findById(1)).thenReturn(Optional.of(userPlant));

        // Act and Assert
        mockMvc.perform(post("/pflanzeLoeschen")
                .param("userPlantId", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/benutzerPflanzen"));

        verify(userPlantRepository, times(1)).deleteById(1);
    }

    @Test
    @WithMockUser
    void testEditNotes() throws Exception {
        // Arrange
        when(userPlantRepository.findById(1)).thenReturn(Optional.of(userPlant));

        // Act and Assert
        mockMvc.perform(post("/pflanzennotizen")
                .param("userPlantId", "1")
                .param("notes", "Some notes")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/benutzerPflanzen"));

        verify(userPlantRepository, times(1)).updateNotesById("Some notes", 1);
    }
}
