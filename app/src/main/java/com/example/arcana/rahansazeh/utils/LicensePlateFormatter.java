package com.example.arcana.rahansazeh.utils;

import com.example.arcana.rahansazeh.model.LicensePlate;

/**
 * Created by arcana on 11/11/17.
 */

public class LicensePlateFormatter {
    private LicensePlateFormatter(){
    }

    public static String toString(LicensePlate licensePlate) {
        StringBuilder result = new StringBuilder();

        result.append(NumberPadder.formatNumber(licensePlate.getRight(), 3));
        result.append(" ");
        result.append(licensePlate.getType());
        result.append(" ");
        result.append(NumberPadder.formatNumber(licensePlate.getLeft(), 2));

        return result.toString();
    }
}
