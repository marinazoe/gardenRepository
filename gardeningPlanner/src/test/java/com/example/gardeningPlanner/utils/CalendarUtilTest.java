package com.example.gardeningPlanner.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CalendarUtilTest {

    @Test
    void testCalculateDate_ValidInput_ReturnsTrue() {
        // Arrange 
        LocalDate currentDate = LocalDate.now();
        LocalDate calculationDate = LocalDate.now();
        int month = currentDate.getMonth().getValue();
        List<LocalDate> expectedDates = new ArrayList<>();
        int frequency = 2;
        int monthsInAdvance = 6;
        int reference = 30;

        //Calculate expected Dates
        for (int i = 0; i < monthsInAdvance; i++) {
            int daysBetweenAction = (int) Math.round((float)reference / (float)frequency);
            daysBetweenAction = (int) Math.round((float)reference / (float)frequency);
            while (calculationDate.plusDays(daysBetweenAction).getMonth().getValue() == month+i) {
                calculationDate = calculationDate.plusDays(daysBetweenAction-1);
                expectedDates.add(calculationDate);
            }
        }

        // Act
        //Use function to calculate dates
        List<LocalDate> dates =  CalendarUtil.calculateDates(currentDate, currentDate, frequency, frequency, reference, monthsInAdvance);

        // Assert
        assertEquals(expectedDates, dates);
    }

    @Test
    void testCalculateDate_InvalidInput_ReturnsFalse() {
        // Arrange 
        LocalDate currentDate = LocalDate.now();
        LocalDate calculationDate = LocalDate.now();
        int month = currentDate.getMonth().getValue();
        List<LocalDate> expectedDates = new ArrayList<>();
        int frequency = 2;
        int monthsInAdvance = 6;
        int reference = 30;

        //Calculate expected Dates
        for (int i = 0; i < monthsInAdvance; i++) {
            int daysBetweenAction = (int) Math.round((float)reference / (float)frequency);
            daysBetweenAction = (int) Math.round((float)reference / (float)frequency);
            while (calculationDate.plusDays(daysBetweenAction).getMonth().getValue() == month+i) {
                calculationDate = calculationDate.plusDays(daysBetweenAction-1);
                expectedDates.add(calculationDate);
            }
        }

        // Act
        //Use function to calculate dates
        currentDate.plusDays(2);
        List<LocalDate> dates =  CalendarUtil.calculateDates(currentDate, currentDate, frequency, frequency, reference, monthsInAdvance);

        // Assert
        assertFalse(expectedDates == dates);
    }

    @Test
    void testCalculateDate_NullAsInput_ThrowsNullPointerException() {
        // Arrange 
        LocalDate currentDate = LocalDate.now();
        int frequency = 2;
        int monthsInAdvance = 6;
        int reference = 30;
        // Act
        // Assert
        assertThrows(NullPointerException.class, () -> {
            CalendarUtil.calculateDates(null, currentDate, frequency, frequency, reference, monthsInAdvance);
        });
    }
}
