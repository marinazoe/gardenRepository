package com.example.gardeningPlanner.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class CalendarUtil {

    /*
     * Calculates all dates at which actions need to be taken, either watering or fertilizing, in the current month and the next month. 
     * frequencyReference need to be 30 if it refernce to once a month. 
     */
    public static List<LocalDate> calculateDates(LocalDate addedDate, LocalDate currentDate, int frequencySummer, int frequencyWinter, int frequencyReference, int monthsInAdvance) {

        List<LocalDate> dates = new ArrayList<>();
        LocalDate calculationDate = addedDate;
        int month = calculationDate.getMonth().getValue();
        int frequency = calculatefrequency(month, 0, frequencyWinter, frequencySummer);
        int daysBetweenAction = (int) Math.round((float)frequencyReference / (float)frequency);

        //Skips from the date the plant was added to then current date while keeping the same cycle
        while (calculationDate.plusDays(daysBetweenAction).isBefore(currentDate)){
            calculationDate = calculationDate.plusDays(daysBetweenAction-1);
            month = calculationDate.getMonth().getValue();
            frequency = calculatefrequency(month, 0, frequencyWinter, frequencySummer);
            daysBetweenAction = (int) Math.round((float)frequencyReference / (float)frequency);
        }

        //Update to current month
        month = currentDate.getMonth().getValue();

        //i defines how many months should be calculated
        for (int i = 0; i < monthsInAdvance; i++) {
            frequency = calculatefrequency(month, i, frequencyWinter, frequencySummer);
            //Uses the frequencies to calculate the days between actions 
            daysBetweenAction = (int) Math.round((float)frequencyReference / (float)frequency);
            while (calculationDate.plusDays(daysBetweenAction).getMonth().getValue() == month+i) {
                calculationDate = calculationDate.plusDays(daysBetweenAction-1);
                dates.add(calculationDate);
            }
        }
        return dates;
    }
    
    private static int calculatefrequency(int month, int counter, int frequencyWinter, int frequencySummer ){
        //Assuming winter starts in November and summer starts in May
        int winterBeginningMonth = 11;
        int summerBeginningMonth = 5;
        if (month+counter >= winterBeginningMonth || month+counter < summerBeginningMonth) {
            return frequencyWinter;
        } else {
            return frequencySummer;
        }
    }
}
