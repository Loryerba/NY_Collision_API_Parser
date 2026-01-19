package org.example.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Util {


    public static Integer parseInt(String value) {
        try {
            return value == null || value.isEmpty() ? null : Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }


    public static Double parseDouble(String value) {
        try {
            return value == null || value.isEmpty() ? null : Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }


    public static LocalDate parseDate(String dateStr) {
        String[] parts = dateStr.split("[/\\\\-]");
        int day = -1, month = -1, year = -1;

        int[] numbers = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();

        for (int n : numbers) {
            if (n >= 1000 && year == -1) year = n;
            else if (n >= 1 && n <= 12 && month == -1) month = n;
            else if (n >= 1 && n <= 31 && day == -1) day = n;
        }

        return LocalDate.of(year, month, day);
    }

    public static LocalTime parseTime(String timeStr){
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("H:mm"));
    }

}
