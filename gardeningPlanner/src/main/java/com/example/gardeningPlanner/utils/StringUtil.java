package com.example.gardeningPlanner.utils;

import java.util.regex.*;

public final class StringUtil {

    public static boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }

    public static String trim(String value) {
        return value != null ? value.trim() : null;
    }

    // Code copied from
    // https://www.javatpoint.com/java-email-validation#:~:text=Email%20Validation%20Permitted%20by%20RFC%205322
    // on the 02.06.2024
    public static boolean isEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}