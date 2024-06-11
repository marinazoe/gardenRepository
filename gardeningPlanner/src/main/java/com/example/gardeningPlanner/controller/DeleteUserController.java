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

    private IUserRepository iUserRepository;

    public DeleteUserController(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @GetMapping("/confirm")
    public String confirmDelete(Model model) {
        return "confirm";
    }

    @PostMapping("/user/delete")
    public String deleteUser(Authentication authentication,
            @AuthenticationPrincipal UserAccountDetails user) {

        var userDeletion = iUserRepository.findById(user.getId());

        if (userDeletion.isPresent()) {
            if (userDeletion.get().getUsername().equals(user.getUsername())) {
                try {
                    iUserRepository.deleteById(userDeletion.get().getId());
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
