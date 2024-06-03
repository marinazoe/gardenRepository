package com.example.gardeningPlanner.controller;

import java.util.Locale;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.utils.StringUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
class RegisterController {

    private static final String REGISTER_ENDPOINT = "/register";

    private static final String REGISTER_FILENAME = "login_register";

    private static final String CALANDER_FILENAME = "redirect:/calender";

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private IUserRepository iUserRepository;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    public RegisterController(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            IUserRepository iUserRepository) {

        this.iUserRepository = iUserRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * Get request for the register/login site
     */
    @GetMapping(REGISTER_ENDPOINT)
    public String register(Model model) {
        return REGISTER_FILENAME;
    }

    /*
     * Post request for the Register/login page
     */

    @PostMapping(REGISTER_ENDPOINT)
    public String registerNewUserAccount(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            Locale locale,
            String username,
            String password,
            String password2,
            String email) {

        // Check if both passwords are the same
        if (!password.equals(password2)) {
            // return to register with error message
            return REGISTER_FILENAME;
        }
        if (!StringUtil.isEmail(email)) {
            // return to register with error message
            return REGISTER_FILENAME;
        }
        final var usernameTrimmed = StringUtil.trim(username);
        try { // with database connection
            var invalidInput = StringUtil.isEmpty(usernameTrimmed) || StringUtil.isEmpty(password);
            if (invalidInput || userAlreadyExistsWith(usernameTrimmed)) {
                // return to register with error message
                return REGISTER_FILENAME;
            }
            saveNewUser(usernameTrimmed, password, email);
        } catch (Exception e) {
            // return to register with error message
            return REGISTER_FILENAME;
        }

        // If account created successful, autologin
        autoLoginNewUser(request, response, usernameTrimmed, password);

        // Direct to Home Site
        return CALANDER_FILENAME;
    }

    private boolean userAlreadyExistsWith(String username) {
        return iUserRepository.findByUsername(username).isPresent();
    }

    private void saveNewUser(String username, String password, String email) {
        var newUser = new UserAccount(username, passwordEncoder.encode(password), email);
        iUserRepository.save(newUser);
    }

    /*
     * Automatic Login Function
     */
    private void autoLoginNewUser(
            HttpServletRequest request,
            HttpServletResponse response,
            String username,
            String password) {

        var token = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }
}