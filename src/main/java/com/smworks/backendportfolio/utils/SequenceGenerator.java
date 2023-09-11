package com.smworks.backendportfolio.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class SequenceGenerator {

    public static String generateUserId() {
        return getDateTime() + generateRandomNumbers();
    }

    private static String generateRandomNumbers() {
        Random random = new Random();
        int randomNumber = random.nextInt(9999 - 1000) + 1000;
        return String.valueOf(randomNumber);
    }

    private static String getDateTime() {
        return DateTimeFormatter.ofPattern("yyMMddhhmmss").format(LocalDateTime.now());
    }
}
