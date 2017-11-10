package com.example.arcana.rahansazeh.model;

import java.io.Serializable;

/**
 * Created by arcana on 11/7/17.
 */

public class Time implements Serializable {
    private int hour;
    private int minute;
    private int second;

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Time(String str) {
        String[] parts = str.split(":");
        int[] parsed = new int[3];

        for (int i = 0; i < 3; i++) {
            parsed[i] = 0;
        }

        for (int i = 0; i < Math.min(parts.length, 3); i++) {
            parsed[i] = Integer.parseInt(parts[i]);
        }

        this.hour = parsed[0];
        this.minute = parsed[1];
        this.second = parsed[2];
    }

    public Time() {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    private static String formatNumber(int number) {
        if (number < 10) {
            return "0" + number;
        }
        else {
            return "" + number;
        }
    }

    @Override
    public String toString() {
        return formatNumber(hour) + ":" + formatNumber(minute) + ":" + formatNumber(second);
    }
}
