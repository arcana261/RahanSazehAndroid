package com.example.arcana.rahansazeh.model;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.io.Serializable;

/**
 * Created by arcana on 11/7/17.
 */

public class LicensePlate {
    private int left;
    private char type;
    private int right;
    private int nationalCode;

    public LicensePlate(int left, char type, int right, int nationalCode) {
        this.left = left;
        this.type = type;
        this.right = right;
        this.nationalCode = nationalCode;
    }

    public LicensePlate(int left, char type, int right) {
        this.left = left;
        this.type = type;
        this.right = right;
        this.nationalCode = 0;
    }

    public LicensePlate(int left, int right) {
        this.left = left;
        this.type = 'ت';
        this.right = right;
        this.nationalCode = 0;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(int nationalCode) {
        this.nationalCode = nationalCode;
    }

    public static class Converter implements PropertyConverter<LicensePlate, String> {

        @Override
        public LicensePlate convertToEntityProperty(String databaseValue) {
            if (databaseValue.length() != 8) {
                throw new IllegalArgumentException(databaseValue);
            }

            String left = databaseValue.substring(0, 2);
            char type = databaseValue.charAt(2);
            String right = databaseValue.substring(3, 6);
            String nationalCode = databaseValue.substring(6, 8);

            return new LicensePlate(Integer.parseInt(left), type, Integer.parseInt(right),
                    Integer.parseInt(nationalCode));
        }

        private String formatValue(String value, int length) {
            StringBuilder valueBuilder = new StringBuilder(value);
            while (valueBuilder.length() < length) {
                valueBuilder.insert(0, "0");
            }
            value = valueBuilder.toString();

            return value;
        }

        private String formatInt(int value, int length) {
            return formatValue("" +value, length);
        }

        @Override
        public String convertToDatabaseValue(LicensePlate entityProperty) {
            StringBuilder builder = new StringBuilder();
            builder.append(formatInt(entityProperty.getLeft(), 2));
            builder.append(entityProperty.getType());
            builder.append(formatInt(entityProperty.getRight(), 3));
            builder.append(formatInt(entityProperty.getNationalCode(), 2));

            return builder.toString();
        }
    }
}
