package com.example.gardeningPlanner.utils;

public final class StringUtil {

    /*
     * Utilities file
     */
    private StringUtil() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }

    public static String trim(String value) {
        return value != null ? value.trim() : null;
    }
}