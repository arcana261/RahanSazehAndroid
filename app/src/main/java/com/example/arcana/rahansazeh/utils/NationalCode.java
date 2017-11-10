package com.example.arcana.rahansazeh.utils;

/**
 * Created by arcana on 11/7/17.
 */

public class NationalCode {
    public static boolean isValidIranianNationalCode(String input) {
        // check if input has 10 digits that all of them are not equal
        if (!input.matches("^\\d{10}$"))
            return false;

        int check = Integer.parseInt(input.substring(9, 10));

        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += Integer.parseInt(input.substring(i, i + 1)) * (10 - i);
        }

        sum = sum % 11;

        return (sum < 2 && check == sum) || (sum >= 2 && check + sum == 11);
    }
}
