package com.example.demo.Until;

public class CreateExpeptionMessage {
    public static String CreateExpeptionMessageString(Exception ex) {
        String s = "Error: " + ex.getMessage();
        return s;
    }
}
