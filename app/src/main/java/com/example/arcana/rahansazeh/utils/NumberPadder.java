package com.example.arcana.rahansazeh.utils;

/**
 * Created by arcana on 11/11/17.
 */

public class NumberPadder {
    private NumberPadder() {
    }

    public static String formatValue(String s, int length) {
        if (s.length() >= length) {
            return s;
        }
        else {
            StringBuilder result = new StringBuilder();
            int count = length - s.length();

            for (int i = 0; i < count; i++) {
                result.append('0');
            }

            result.append(s);

            return result.toString();
        }
    }

    public static String formatNumber(int n, int length) {
        return formatValue("" + n, length);
    }
}
