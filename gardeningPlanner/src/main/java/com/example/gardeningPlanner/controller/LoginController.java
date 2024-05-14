package com.example.gardeningPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static final String LOGIN_ENDPOINT = "/login";

    private static final String LOGIN_FILENAME = "login_register";

    @GetMapping(value = LOGIN_ENDPOINT, params = "!error")
    public String login(Model model) {
        return LOGIN_FILENAME; // name of Thymeleaf-Template
    }

    @RequestMapping(method = RequestMethod.GET, value = LOGIN_ENDPOINT, params = "error=true")
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("error", true); // true: For Default Error Message
        return LOGIN_FILENAME;
    }

}
