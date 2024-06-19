package com.example.gardeningPlanner.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.authentication.UserAccountDetails;

@Controller
public class DeleteUserController {

    private static final String DELETE_USER_ENDPOINT = "/benutzerLoeschen";

    private static final String DELETE_USER_FILENAME = "deleteUser";

    private static final String REDIRECT = "redirect:";

    private static final String LOGIN_ENDPOINT = "/anmeldung";

    private IUserRepository iUserRepository;

    public DeleteUserController(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @GetMapping(DELETE_USER_ENDPOINT)
    public String confirmDelete(Model model) {
        return DELETE_USER_FILENAME;
    }

    @PostMapping(DELETE_USER_ENDPOINT)
    public String deleteUser(Model model, Authentication authentication,
            @AuthenticationPrincipal UserAccountDetails user) {

        var userDeletion = iUserRepository.findById(user.getId());

        if (userDeletion.isPresent()) {
            if (userDeletion.get().getUsername().equals(user.getUsername())) {
                try {
                    iUserRepository.deleteById(userDeletion.get().getId());
                    SecurityContextHolder.clearContext(); // Benutzer abmelden
                    return REDIRECT + LOGIN_ENDPOINT;
                } catch (Exception e) {
                    model.addAttribute("error", e.getMessage());
                    return DELETE_USER_FILENAME;
                }
            } else {
                model.addAttribute("error", "Fehler: Unberechtigte Anfrage");
                return DELETE_USER_FILENAME;
            }
        } else {
            model.addAttribute("error", "Fehler: Benutzer nicht gefunden");
            return DELETE_USER_FILENAME;
        }
    }
}
