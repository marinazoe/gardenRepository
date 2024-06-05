package com.example.gardeningPlanner.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.gardeningPlanner.Repositories.IPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.Plant;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;
import com.example.gardeningPlanner.authentication.UserAccountDetails;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PlantListController {

    private IPlantRepository iPlantRepository;
    private IUserRepository iUserRepository;
    private IUserPlantRepository iUserPlantRepository;

    PlantListController(IPlantRepository iPlantRepository, IUserRepository iUserRepository, IUserPlantRepository iUserPlantRepository) {
        this.iPlantRepository = iPlantRepository;
        this.iUserRepository = iUserRepository;
        this.iUserPlantRepository = iUserPlantRepository;
    }
    
    /*
     * Get request for the plant list site
     */
    @GetMapping("/plant_list")
    public String plantList(Model model, Authentication authentication,
    @AuthenticationPrincipal UserAccountDetails user) {
        model.addAttribute("list", getAllPlants());
        return "placeholder_plant_list";
    }

    /*
     * Post request for adding a plant to the User
     */
    @PostMapping("/add_Plant")
    public String addPlant(Model model, Authentication authentication,
    @AuthenticationPrincipal UserAccountDetails user, int plantId) {

        var insertPlant = getPlantFromPlantTable(plantId);

        var currentUser = getCurrentUser(user);

        saveNewUserPlant(insertPlant, currentUser);

        return "redirect:/plant_list";
    }

    private List<Plant> getAllPlants() {
        return iPlantRepository.findAll();
    }

    private Plant getPlantFromPlantTable(int plantId) {
        return iPlantRepository.findById(plantId).orElseThrow(NoSuchElementException::new);
    }

    private UserAccount getCurrentUser(UserAccountDetails user) {
        return iUserRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
    }
    
    private void saveNewUserPlant(Plant plant, UserAccount userAccount) {
        var newUserPlant = new UserPlant(plant , userAccount);
        iUserPlantRepository.save(newUserPlant);
    }
}
