package com.example.gardeningPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static final String LOGIN_ENDPOINT = "/anmeldung";

    private static final String LOGIN_FILENAME = "login_register";

    /*
     * Get request for login site
     */
    @GetMapping(value = LOGIN_ENDPOINT, params = {"!error", "!logout"})
    public String login(Model model) {
        return LOGIN_FILENAME;
    }

    /*
     * Get request for login site if error occurs
     */
    @GetMapping(value = LOGIN_ENDPOINT, params = {"error=true" , "!logout"})
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("error", "Falscher Benutzername oder falsches Passwort.");
        return LOGIN_FILENAME;
    }

    /*
     * Get request for login site after logout
     */
    @GetMapping(value = LOGIN_ENDPOINT, params = {"!error", "abmelden=true"})
    public String logout(HttpServletRequest request, Model model) {
        model.addAttribute("logout", "Sie sind abgemeldet.");
        return LOGIN_FILENAME;
    }

}
