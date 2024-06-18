package com.example.gardeningPlanner.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class CalendarUtil {

    /*
     * Calculates all dates at which actions need to be taken, either watering or fertilizing, in the current month and the next month. 
     * frequencyReference need to be 30 if it refernce to once a month. 
     */
    public static List<LocalDate> calculateDates(LocalDate addedDate, int frequencySummer, int frequencyWinter, int frequencyReference) {

        List<LocalDate> dates = new ArrayList<>();
        LocalDate calculationDate = addedDate;
        int month = calculationDate.getMonth().getValue();
        LocalDate currentDate = LocalDate.now();
        int frequency = calculatefrequency(month, 0, frequencyWinter, frequencySummer);
        int daysBetweenAction = (int) Math.round((float)frequencyReference / (float)frequency);

        //Skips from the date the plant was added to then current date while keeping the same cycle
        while (calculationDate.plusDays(daysBetweenAction).isBefore(currentDate)){
            calculationDate = calculationDate.plusDays(daysBetweenAction);
            month = calculationDate.getMonth().getValue();
            frequency = calculatefrequency(month, 0, frequencyWinter, frequencySummer);
            daysBetweenAction = (int) Math.round((float)frequencyReference / (float)frequency);
        }

        //Update to current month
        month = currentDate.getMonth().getValue();

        //2 runs, one for the current month and one for the next month
        for (int i = 0; i < 6; i++) {
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
    
    private static int calculatefrequency(int month, int i, int frequencyWinter, int frequencySummer ){
        //Assuming winter starts in November and summer starts in May
        int winterBeginningMonth = 11;
        int summerBeginningMonth = 5;
        if (month+i >= winterBeginningMonth || month+i < summerBeginningMonth) {
            return frequencyWinter;
        } else {
            return frequencySummer;
        }
    }
}
