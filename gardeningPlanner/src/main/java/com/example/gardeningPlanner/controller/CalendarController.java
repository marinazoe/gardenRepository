package com.example.gardeningPlanner.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;
import com.example.gardeningPlanner.authentication.UserAccountDetails;
import com.example.gardeningPlanner.utils.CalendarUtil;

@Controller
public class CalendarController {

    public record CalenderPlant(String name, String nickname, List<LocalDate> wateringDates, List<LocalDate> fertilizeDates) {}
    public record DayInfo(int day, List<CalenderPlant> plantsToWater, List<CalenderPlant> plantsToFertilize) {}

    private static final String CALENDAR_ENDPOINT = "/kalender";

    private IUserRepository iUserRepository;
    private IUserPlantRepository iUserPlantRepository;

    CalendarController(IUserRepository iUserRepository, IUserPlantRepository iUserPlantRepository) {
        this.iUserRepository = iUserRepository;
        this.iUserPlantRepository = iUserPlantRepository;
    }

    @GetMapping(CALENDAR_ENDPOINT)
    public String home(@RequestParam(value = "monthOffset", defaultValue = "0") int monthOffset, Model model,
                       @AuthenticationPrincipal UserAccountDetails user) {
        var currentUser = getCurrentUser(user);
        var currentUserPlants = findAllUserPlants(currentUser);

        List<CalenderPlant> calendarPlants = new ArrayList<>();

        for (UserPlant userPlant : currentUserPlants) {
            var wateringDates = CalendarUtil.calculateDates(userPlant.getAdded_date(), LocalDate.now(), userPlant.getPlant().getWater_summer(), userPlant.getPlant().getWater_winter(), 30, 6);
            var fertilizeDates = CalendarUtil.calculateDates(userPlant.getAdded_date(), LocalDate.now(), userPlant.getPlant().getFertilize_summer(), userPlant.getPlant().getFertilize_winter(), 30, 6);
            String plantName = userPlant.getPlant().getName();
            String plantNickname = userPlant.getNickname();
            calendarPlants.add(new CalenderPlant(plantName, plantNickname, wateringDates, fertilizeDates));
        }

        LocalDate targetDate = LocalDate.now().plusMonths(monthOffset);
        DayInfo[] monthInfos = getMonthInfos(calendarPlants, targetDate);

        String monthName = getMonthName(targetDate.getMonth());

        model.addAttribute("monthInfos", monthInfos);
        model.addAttribute("monthName", monthName);
        model.addAttribute("calenderPlants", calendarPlants);
        model.addAttribute("message", user.getUsername());
        model.addAttribute("monthOffset", monthOffset);
        return "calendar";
    }

    private DayInfo[] getMonthInfos(List<CalenderPlant> calendarPlants, LocalDate date) {
        TemporalAdjuster lastDay = TemporalAdjusters.lastDayOfMonth();
        int dayCount = date.with(lastDay).getDayOfMonth();

        TemporalAdjuster firstDay = TemporalAdjusters.firstDayOfMonth();
        DayOfWeek firstWeekday = date.with(firstDay).getDayOfWeek();

        int year = date.getYear();
        int month = date.getMonthValue();

        return getDayInfos(year, month, getFirstWeekday(firstWeekday), dayCount, calendarPlants);
    }

    public DayInfo[] getDayInfos(int year, int month, int firstWeekday, int countDays, List<CalenderPlant> calenderPlants) {
        DayInfo[] dayInfos = new DayInfo[countDays + firstWeekday];

        for (int i = 0; i < firstWeekday; i++) {
            dayInfos[i] = createEmptyDayInfo(0);
        }

        for (int i = 0; i < countDays; i++) {
            LocalDate day = LocalDate.of(year, month, i + 1);
            dayInfos[i + firstWeekday] = new DayInfo(i + 1, getPlantsToWater(day, calenderPlants), getPlantsToFertilize(day, calenderPlants));
        }
        return dayInfos;
    }

    private DayInfo createEmptyDayInfo(int ignoreOrBlank) {
        List<CalenderPlant> emptyPlantList = List.of();
        return new DayInfo(ignoreOrBlank, emptyPlantList, emptyPlantList);
    }

    private UserAccount getCurrentUser(UserAccountDetails user) {
        return iUserRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
    }

    private List<UserPlant> findAllUserPlants(UserAccount userAccount) {
        return iUserPlantRepository.findAllByUserAccount(userAccount).orElseThrow(NoSuchElementException::new);
    }

    private int getFirstWeekday(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return 0;
            case TUESDAY:
                return 1;
            case WEDNESDAY:
                return 2;
            case THURSDAY:
                return 3;
            case FRIDAY:
                return 4;
            case SATURDAY:
                return 5;
            case SUNDAY:
                return 6;
            default:
                return -1;
        }
    }

    private String getMonthName(Month month) {
        switch (month) {
            case JANUARY:
                return "Januar";
            case FEBRUARY:
                return "Februar";
            case MARCH:
                return "März";
            case APRIL:
                return "April";
            case MAY:
                return "Mai";
            case JUNE:
                return "Juni";
            case JULY:
                return "Juli";
            case AUGUST:
                return "August";
            case SEPTEMBER:
                return "September";
            case OCTOBER:
                return "Oktober";
            case NOVEMBER:
                return "November";
            case DECEMBER:
                return "Dezember";
            default:
                return "";
        }
    }

    private List<CalenderPlant> getPlantsToWater(LocalDate date, List<CalenderPlant> calenderPlants) {
        List<CalenderPlant> results = new ArrayList<>();
        for (CalenderPlant plant : calenderPlants) {
            if (plant.wateringDates.contains(date)) {
                results.add(plant);
            }
        }
        return results;
    }

    private List<CalenderPlant> getPlantsToFertilize(LocalDate date, List<CalenderPlant> calenderPlants) {
        List<CalenderPlant> results = new ArrayList<>();
        for (CalenderPlant plant : calenderPlants) {
            if (plant.fertilizeDates.contains(date)) {
                results.add(plant);
            }
        }
        return results;
    }
}