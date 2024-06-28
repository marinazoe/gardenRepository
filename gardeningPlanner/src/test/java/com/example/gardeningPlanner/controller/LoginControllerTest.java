package com.example.gardeningPlanner.controller;

import static com.example.gardeningPlanner.SecurityMockMvc.HttpMethod.GET;
import static org.assertj.core.api.Assertions.assertThat;
import static com.example.gardeningPlanner.UserAccountDetailsTestUtil.createMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import com.example.gardeningPlanner.SecurityFilterConfig;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.authentication.AuthentificationConfig;
import com.example.gardeningPlanner.SecurityMockMvc;

@Import({SecurityFilterConfig.class, AuthentificationConfig.class, H2ConsoleProperties.class})
@WebMvcTest(LoginController.class)
public class LoginControllerTest extends SecurityMockMvc{

    @MockBean
    private IUserRepository iUserRepository;

    @Test
    void testNonAuthenticatedUserCanLogin() throws Exception{
        // Arrange and Act
        var html = html200From(request(GET, "/anmeldung"));

        // Assert
        assertThat(html).contains("Anmelden", "Benutzername", "Passwort");
    }

    @Test
    void testAuthenticatedUserCanLogin() throws Exception{
        // Arrange and Act
        var mockUser = createMockUser("mockUser", "mock@email.test");
        var html = html200From(request(GET, "/anmeldung").with(user(mockUser)));

        // Assert
        assertThat(html).contains("Anmelden", "Benutzername", "Passwort");
    }

    @Test
    void testLoginFail() throws Exception{
            // Arrange and Act
            var mockUser = createMockUser("mockUser", "mock@email.test");
            var html = html200From(request(GET, "/anmeldung?error=true").with(user(mockUser)));
        
            // Assert
            assertThat(html).contains("Falscher Benutzername oder falsches Passwort.");
    }

    @Test
    void testLogout() throws Exception{
            // Arrange and Act
            var html = html200From(request(GET, "/anmeldung?abmelden=true"));
        
            // Assert
            assertThat(html).contains("Sie sind abgemeldet.");
    }
}
