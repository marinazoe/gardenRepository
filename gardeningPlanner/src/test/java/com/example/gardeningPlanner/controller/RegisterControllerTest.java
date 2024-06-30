package com.example.gardeningPlanner.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.example.gardeningPlanner.SecurityFilterConfig;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.authentication.AuthentificationConfig;

@Import({SecurityFilterConfig.class, AuthentificationConfig.class, H2ConsoleProperties.class})
@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSuccessfulRegistration2() throws Exception {

        // Arrange
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserAccount.class))).thenReturn(new UserAccount("newUser", "encodedPassword", "newuser@example.com"));

        // Act
        mockMvc.perform(post("/registrierung")
                .param("username", "newUser")
                .param("password", "password123")
                .param("password2", "password123")
                .param("email", "newuser@example.com"))

            //Assert
            .andExpect(status().is3xxRedirection());
            //.andExpect(redirectedUrl("http://localhost/kalender"));

        verify(userRepository,atLeastOnce()).findByUsername("newUser");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(UserAccount.class));
    }


    @Test
    public void testRegistrationWithExistingUser() throws Exception {

        //Arrange
        UserAccount mockUserAccount = mock(UserAccount.class);
        when(mockUserAccount.getUsername()).thenReturn("existingUser");
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(mockUserAccount));

        //Act
        mockMvc.perform(post("/registrierung")
                .param("username", "existingUser")
                .param("password", "password123")
                .param("password2", "password123")
                .param("email", "existinguser@example.com"))

            //Assert
            .andExpect(status().isOk())
            .andExpect(view().name("login_register"))
            .andExpect(model().attribute("used", true));
    }

    @Test
    public void testRegistrationWithPasswordMismatch() throws Exception {

        //Act
        mockMvc.perform(post("/registrierung")
                .param("username", "newUser")
                .param("password", "password123")
                .param("password2", "password124")
                .param("email", "newuser@example.com"))

            //Assert
            .andExpect(status().isOk())
            .andExpect(view().name("login_register"))
            .andExpect(model().attribute("pw", true));
    }

    @Test
    public void testRegistrationWithInvalidEmail() throws Exception {

        //Act
        mockMvc.perform(post("/registrierung")
                .param("username", "newUser")
                .param("password", "password123")
                .param("password2", "password123")
                .param("email", "newuser"))

            //Assert
            .andExpect(status().isOk())
            .andExpect(view().name("login_register"))
            .andExpect(model().attribute("email", true));
    }

}




