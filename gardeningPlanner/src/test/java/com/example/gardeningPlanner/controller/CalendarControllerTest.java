package com.example.gardeningPlanner.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.example.gardeningPlanner.Repositories.IUserPlantRepository;
import com.example.gardeningPlanner.Repositories.IUserRepository;
import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.Tables.UserPlant;
import com.example.gardeningPlanner.Tables.Plant;
import com.example.gardeningPlanner.authentication.UserAccountDetails;
import com.example.gardeningPlanner.controller.CalendarController.CalenderPlant;
import com.example.gardeningPlanner.controller.CalendarController.DayInfo;

public class CalendarControllerTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserPlantRepository userPlantRepository;

    @Mock
    private Model model;

    @InjectMocks
    private CalendarController calendarController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHome_WithValidUser_ShouldReturnCalendarView() {
        UserAccountDetails userDetails = mock(UserAccountDetails.class);
        when(userDetails.getId()).thenReturn(1);
        when(userDetails.getUsername()).thenReturn("testUser");

        UserAccount userAccount = mock(UserAccount.class);
        when(userRepository.findById(1)).thenReturn(Optional.of(userAccount));

        List<UserPlant> userPlants = new ArrayList<>();
        UserPlant userPlant = mock(UserPlant.class);
        userPlants.add(userPlant);
        when(userPlantRepository.findAllByUserAccount(userAccount)).thenReturn(Optional.of(userPlants));

        when(userPlant.getAdded_date()).thenReturn(LocalDate.now().minusDays(30));
        when(userPlant.getPlant()).thenReturn(mock(Plant.class));

        String viewName = calendarController.home(0, model, userDetails);

        assertEquals("calendar", viewName);
        verify(model).addAttribute(eq("monthInfos"), any());
        verify(model).addAttribute(eq("monthName"), anyString());
        verify(model).addAttribute(eq("calenderPlants"), any());
        verify(model).addAttribute(eq("message"), eq("testUser"));
        verify(model).addAttribute(eq("monthOffset"), eq(0));
    }

    @Test
    void testHome_WithInvalidUser_ShouldThrowException() {
        UserAccount userAccount = mock(UserAccount.class);
        when(userAccount.getId()).thenReturn(1);
        UserAccountDetails userDetails = new UserAccountDetails(userAccount);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            calendarController.home(0, model, userDetails);
        });
    }

    @Test
    void testGetDayInfos_ShouldReturnCorrectDayInfos() {
        List<CalenderPlant> calendarPlants = new ArrayList<>();
        int year = 2023;
        int month = 7; // July
        int firstWeekday = 0; // Monday
        int countDays = 31;

        DayInfo[] dayInfos = calendarController.getDayInfos(year, month, firstWeekday, countDays, calendarPlants);

        assertNotNull(dayInfos);
        assertEquals(countDays, dayInfos.length - firstWeekday);
    }

    @Test
    void testGetMonthName_ShouldReturnCorrectMonthName() {
        assertEquals("Januar", calendarController.getMonthName(Month.JANUARY));
        assertEquals("Februar", calendarController.getMonthName(Month.FEBRUARY));
        assertEquals("März", calendarController.getMonthName(Month.MARCH));
        assertEquals("April", calendarController.getMonthName(Month.APRIL));
        assertEquals("Mai", calendarController.getMonthName(Month.MAY));
        assertEquals("Juni", calendarController.getMonthName(Month.JUNE));
        assertEquals("Juli", calendarController.getMonthName(Month.JULY));
        assertEquals("August", calendarController.getMonthName(Month.AUGUST));
        assertEquals("September", calendarController.getMonthName(Month.SEPTEMBER));
        assertEquals("Oktober", calendarController.getMonthName(Month.OCTOBER));
        assertEquals("November", calendarController.getMonthName(Month.NOVEMBER));
        assertEquals("Dezember", calendarController.getMonthName(Month.DECEMBER));
    }

    @Test
    void testGetFirstWeekday_ShouldReturnCorrectIndex() {
        assertEquals(0, calendarController.getFirstWeekday(DayOfWeek.MONDAY));
        assertEquals(1, calendarController.getFirstWeekday(DayOfWeek.TUESDAY));
        assertEquals(2, calendarController.getFirstWeekday(DayOfWeek.WEDNESDAY));
        assertEquals(3, calendarController.getFirstWeekday(DayOfWeek.THURSDAY));
        assertEquals(4, calendarController.getFirstWeekday(DayOfWeek.FRIDAY));
        assertEquals(5, calendarController.getFirstWeekday(DayOfWeek.SATURDAY));
        assertEquals(6, calendarController.getFirstWeekday(DayOfWeek.SUNDAY));
    }

    @Test
    void testGetPlantsToWater_ShouldReturnCorrectPlants() {
        List<CalenderPlant> calendarPlants = new ArrayList<>();
        CalenderPlant plant = new CalenderPlant("Rose", "Rosie", List.of(LocalDate.now()), new ArrayList<>());
        calendarPlants.add(plant);

        List<CalenderPlant> result = calendarController.getPlantsToWater(LocalDate.now(), calendarPlants);

        assertEquals(1, result.size());
        assertEquals("Rose", result.get(0).name());
    }

    @Test
    void testGetPlantsToFertilize_ShouldReturnCorrectPlants() {
        List<CalenderPlant> calendarPlants = new ArrayList<>();
        CalenderPlant plant = new CalenderPlant("Rose", "Rosie", new ArrayList<>(), List.of(LocalDate.now()));
        calendarPlants.add(plant);

        List<CalendarController.CalenderPlant> result = calendarController.getPlantsToFertilize(LocalDate.now(), calendarPlants);

        assertEquals(1, result.size());
        assertEquals("Rose", result.get(0).name());
    }
}
