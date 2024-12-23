package com.echoteam.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String generateHash(String input) {
        try {
            // Створення екземпляру MessageDigest для SHA-256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Отримуємо байти з вхідного рядка
            byte[] bytes = input.getBytes();

            // Оновлюємо MessageDigest з вхідними даними
            messageDigest.update(bytes);

            // Отримуємо хеш як масив байтів
            byte[] hashedBytes = messageDigest.digest();

            // Конвертуємо масив байтів в шістнадцятковий рядок
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                // Конвертуємо байт у двозначний шістнадцятковий формат
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString(); // Повертаємо хеш як рядок
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // В разі помилки при створенні MessageDigest
        }
    }

}
