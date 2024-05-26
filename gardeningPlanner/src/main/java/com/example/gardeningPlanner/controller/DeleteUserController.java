package com.example.gardeningPlanner.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.gardeningPlanner.Repositories.IUserRepository;

@Controller
public class DeleteUserController {

    @Autowired
    private IUserRepository iUserRepository;

    public DeleteUserController(IUserRepository iUserRepository){
        this.iUserRepository = iUserRepository;
    }

    @DeleteMapping("/user/delete/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        var user = iUserRepository.findById(id);
        
        if(user.isPresent()){
            if (user.get().getUsername().equals(currentUsername)) {
                try {
                    iUserRepository.deleteById(id);
                    SecurityContextHolder.clearContext(); // Benutzer abmelden
                    return "User Account erfolgreich gelöscht";
                } catch (Exception e) {
                    return "Fehler beim Löschen des Accounts: " + e.getMessage();
                }
            }
            else {
                return "Fehler: Unberechtigte Anfrage";
            }
        }
        else {
            return "Fehler: Benutzer nicht gefunden";
        }
    }
}
