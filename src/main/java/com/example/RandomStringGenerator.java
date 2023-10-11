package com.example;

import java.util.Random;

public class RandomStringGenerator {

    public static String generateRandomString(int length) {
        // Define the characters from which the random string will be generated
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Create a StringBuilder to build the random string
        StringBuilder randomStringBuilder = new StringBuilder(length);

        // Create a Random object
        Random random = new Random();

        // Generate random characters and append them to the StringBuilder
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomStringBuilder.append(randomChar);
        }

        // Convert the StringBuilder to a String and return it
        return randomStringBuilder.toString();
    }
}