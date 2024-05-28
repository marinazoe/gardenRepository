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

    public DeleteUserController(IUserRepository iUserRepository){
        this.iUserRepository = iUserRepository;
    }

    @PostMapping("/user/delete")
    public String deleteUser(Authentication authentication,
            @AuthenticationPrincipal UserAccountDetails user) {
       // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       // String currentUsername = authentication.getName();
        System.out.println("1");
        var userDeletion = iUserRepository.findById(user.getId());
        System.out.println("2");
        if(userDeletion.isPresent()){
            System.out.println("3");
            if (userDeletion.get().getUsername().equals(user.getUsername())) {
                System.out.println("4");
                try {
                    System.out.println("5");
                    iUserRepository.deleteById(user.getId());
                    System.out.println("6");
                    SecurityContextHolder.clearContext(); // Benutzer abmelden
                    //return "User Account erfolgreich gelöscht";
                    System.out.println("7");
                    return "redirect:/login";
                } catch (Exception e) {
                    System.out.println("8");
                    return "Fehler beim Löschen des Accounts: " + e.getMessage();
                }
            }
            else {
                System.out.println("9");
                return "Fehler: Unberechtigte Anfrage";
            }
        }
        else {
            System.out.println("10");
            return "Fehler: Benutzer nicht gefunden";
        }
    }
}
