package com.example.gardeningPlanner.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;
import com.example.gardeningPlanner.authentication.UserAccountDetails;

@Controller
public class UserPlantsController {

    private static final String USER_PLANTS_ENDPOINT = "/benutzerPflanzen";

    private static final String USER_PLANTS_FILENAME = "user_plants";

    private static final String USER_PLANTS_DELETION_ENDPOINT = "/pflanzeLoeschen";

    private static final String USER_PLANT_CHANGE_NICKNAME = "/pflanzeumbennen";

    private static final String USER_PLANT_EDIT_NOTES = "/pflanzennotizen";

    private static final String REDIRECT = "redirect:";
    
    private IUserRepository iUserRepository;
    private IUserPlantRepository iUserPlantRepository;

    UserPlantsController(IUserRepository iUserRepository, IUserPlantRepository iUserPlantRepository) {
        this.iUserRepository = iUserRepository;
        this.iUserPlantRepository = iUserPlantRepository;
    }

    /*
     * Get request for the user plants site
     */
    @GetMapping(USER_PLANTS_ENDPOINT)
    public String userPlants(Model model, Authentication authentication,
    @AuthenticationPrincipal UserAccountDetails user) {
        var currentUser = getCurrentUser(user);

        var currentUserPlants = findAllUserPlants(currentUser);

        model.addAttribute("userPlants", currentUserPlants);
        return USER_PLANTS_FILENAME;
    }

    /*
     * Post request for deleting a user plant
     */
    @PostMapping(USER_PLANTS_DELETION_ENDPOINT)
    public String deletePlant(Model model, int userPlantId) {
        var plantDeletion = getUserPlant(userPlantId);
        try {
            iUserPlantRepository.deleteById(plantDeletion.getId());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return REDIRECT + USER_PLANTS_ENDPOINT;
    }

    @PostMapping(USER_PLANT_CHANGE_NICKNAME)
    public String changeNickname(Model model, int userPlantId, String nickname){
        var plant = getUserPlant(userPlantId);
        try {
            iUserPlantRepository.updateNicknameById(nickname, plant.getId());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return REDIRECT + USER_PLANTS_ENDPOINT;
    }

    @PostMapping(USER_PLANT_EDIT_NOTES)
    public String editNotes(Model model, int userPlantId, String notes){
        var plant = getUserPlant(userPlantId);
        try {
            iUserPlantRepository.updateNotesById(notes, plant.getId());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return REDIRECT + USER_PLANTS_ENDPOINT;
    }


    private UserAccount getCurrentUser(UserAccountDetails user) {
        return iUserRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
    }
    
    private List<UserPlant> findAllUserPlants(UserAccount userAccount) {
        return iUserPlantRepository.findAllByUserAccount(userAccount).orElseThrow(NoSuchElementException::new);
    }

    private UserPlant getUserPlant(int userPlantId) {
        return iUserPlantRepository.findById(userPlantId).orElseThrow(NoSuchElementException::new);
    }
}
