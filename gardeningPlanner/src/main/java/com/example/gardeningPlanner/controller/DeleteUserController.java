package com.example.gardeningPlanner.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.authentication.UserAccountDetails;

@Controller
public class DeleteUserController {

    private IUserRepository iUserRepository;

    public DeleteUserController(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @PostMapping("/user/delete")
    public String deleteUser(Authentication authentication,
            @AuthenticationPrincipal UserAccountDetails user) {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // String currentUsername = authentication.getName();
        var userDeletion = iUserRepository.findById(user.getId());
        if (userDeletion.isPresent()) {
            if (userDeletion.get().getUsername().equals(user.getUsername())) {
                try {
                    iUserRepository.deleteById(user.getId());
                    SecurityContextHolder.clearContext(); // Benutzer abmelden
                    // return "User Account erfolgreich gelöscht";
                    return "redirect:/login";
                } catch (Exception e) {
                    return "Fehler beim Löschen des Accounts: " + e.getMessage();
                }
            } else {
                return "Fehler: Unberechtigte Anfrage";
            }
        } else {
            return "Fehler: Benutzer nicht gefunden";
        }
    }
}
