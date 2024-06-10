package com.example.gardeningPlanner.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.gardeningPlanner.authentication.UserAccountDetails;

@Controller
public class PlaceholderCalendarController {
    
    @GetMapping("/calendar")
    public String home(Model model, Authentication authentication,
    @AuthenticationPrincipal UserAccountDetails user) {
        model.addAttribute("message", user.getUsername());
        //System.out.println(user.getUsername());
        return "placeholder_calendar";
    }
}
