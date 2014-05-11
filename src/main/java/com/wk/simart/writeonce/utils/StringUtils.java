package com.wk.simart.writeonce.utils;

public final class StringUtils {

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String uncapitalize(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    private StringUtils() {
    }
}
