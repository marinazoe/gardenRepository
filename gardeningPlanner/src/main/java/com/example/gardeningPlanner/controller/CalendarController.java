package com.example.gardeningPlanner.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;
import com.example.gardeningPlanner.authentication.UserAccountDetails;
import com.example.gardeningPlanner.utils.CalendarUtil;

@Controller
public class CalendarController {

    public record CalenderPlant(String name, String nickname, List<LocalDate> wateringDates, List<LocalDate> fertilizeDates) {
    }

    private static final String CALENDAR_ENDPOINT = "/calendar";

    private static final String CALENDAR_FILENAME = "calendar";

    private IUserRepository iUserRepository;
    private IUserPlantRepository iUserPlantRepository;

    CalendarController(IUserRepository iUserRepository, IUserPlantRepository iUserPlantRepository) {
        this.iUserRepository = iUserRepository;
        this.iUserPlantRepository = iUserPlantRepository;
    }
    
    @GetMapping(CALENDAR_ENDPOINT)
    public String home(Model model, Authentication authentication,
    @AuthenticationPrincipal UserAccountDetails user) {
        var currentUser = getCurrentUser(user);
        var currentUserPlants = findAllUserPlants(currentUser);

        List<CalenderPlant> calenderPlants = new ArrayList<>();
        
        for (UserPlant userPlant : currentUserPlants) {
            var wateringDates = CalendarUtil.calculateDates(userPlant.getAdded_date(), userPlant.getPlant().getWater_summer(), userPlant.getPlant().getWater_winter(), 30);
            var fertilizeDates = CalendarUtil.calculateDates(userPlant.getAdded_date(), userPlant.getPlant().getFertilize_summer(), userPlant.getPlant().getFertilize_winter(), 30);
            String plantName = userPlant.getPlant().getName();
            String plantNickname = userPlant.getNickname();
            //Creates a record for each plant the user has
            calenderPlants.add(new CalenderPlant(plantName, plantNickname, wateringDates, fertilizeDates));
        }
        model.addAttribute("calenderPlants", calenderPlants);
        model.addAttribute("message", user.getUsername());
        return CALENDAR_FILENAME;
    }










    private UserAccount getCurrentUser(UserAccountDetails user) {
        return iUserRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
    }
    
    private List<UserPlant> findAllUserPlants(UserAccount userAccount) {
        return iUserPlantRepository.findAllByUserAccount(userAccount).orElseThrow(NoSuchElementException::new);
    }
}
