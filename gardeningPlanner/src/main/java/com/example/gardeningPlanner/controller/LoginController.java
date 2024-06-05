package com.example.gardeningPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static final String LOGIN_ENDPOINT = "/login";

    private static final String LOGIN_FILENAME = "login_register";

    @GetMapping(value = LOGIN_ENDPOINT, params = {"!error", "!logout"})
    public String login(Model model) {
        return LOGIN_FILENAME;
    }

    @GetMapping(value = LOGIN_ENDPOINT, params = {"error=true" , "!logout"})
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("error", true); // true: For Error Message
        return LOGIN_FILENAME;
    }

    @GetMapping(value = LOGIN_ENDPOINT, params = {"!error", "logout=true"})
    public String logout(HttpServletRequest request, Model model) {
        model.addAttribute("logout", true); // true: For Logout Message
        return LOGIN_FILENAME;
    }

}
