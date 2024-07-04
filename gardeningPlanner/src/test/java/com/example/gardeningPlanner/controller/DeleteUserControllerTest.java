package com.example.gardeningPlanner.controller;

import static com.example.gardeningPlanner.SecurityMockMvc.HttpMethod.GET;
import static com.example.gardeningPlanner.SecurityMockMvc.HttpMethod.POST;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.gardeningPlanner.SecurityMockMvc;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.authentication.UserAccountDetails;

@WebMvcTest(DeleteUserController.class)
public class DeleteUserControllerTest extends SecurityMockMvc {

    @MockBean
    private IUserRepository userRepository;

    private UserAccount mockUser;
    private UserAccountDetails mockUserDetails;


    @BeforeEach
    public void setUp() {

        mockUser = new UserAccount("testUser", "password");
        mockUser.setId(1);
        mockUserDetails = new UserAccountDetails(mockUser);
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
    }

    @Test
    @WithMockUser
    public void testViewDeleteUser() throws Exception {
        
        // Act & Assert
        mvc.perform(request(GET, "/benutzerLoeschen"))
            .andExpect(status().isOk())
            .andExpect(view().name("deleteUser"));
    }

    @Test
    @WithMockUser
    public void testDeleteUser() throws Exception {

        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        // Act & Assert
        mvc.perform(request(POST, "/benutzerLoeschen")
                .with(user(mockUserDetails)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/anmeldung"));
    }

    @Test
    @WithMockUser
    public void testDeleteUserWithNonExistingUser() throws Exception {

        // Arrange
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        mvc.perform(request(POST, "/benutzerLoeschen")
                .with(user(new UserAccountDetails(new UserAccount("nonExistingUser", "password")))))
                .andExpect(status().isOk())
                .andExpect(view().name("deleteUser"))
                .andExpect(model().attribute("error", "Fehler: Benutzer nicht gefunden"));
    }

    @Test
    @WithMockUser
    public void testDeleteUserWithNonMatchingUsername() throws Exception {

        // Act & Assert
        mvc.perform(request(POST, "/benutzerLoeschen")
                .with(user(new UserAccountDetails(new UserAccount("differentUser", "password")))))
                .andExpect(status().isOk())
                .andExpect(view().name("deleteUser"))
                .andExpect(model().attribute("error", "Fehler: Benutzer nicht gefunden"));
    }
    
}
