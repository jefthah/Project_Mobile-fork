package com.example.healthcare;

public class Utils {
    public static String encodeUsername(String username) {
        return username.replace(".", "%2E");
    }
}
