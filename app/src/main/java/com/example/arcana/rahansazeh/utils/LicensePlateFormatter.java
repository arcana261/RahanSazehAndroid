package com.example.arcana.rahansazeh.utils;

import com.example.arcana.rahansazeh.model.LicensePlate;
import com.gurkashi.lava.lambdas.Predicate;
import com.gurkashi.lava.lambdas.Selector;
import com.gurkashi.lava.queries.stracture.Queriable;

import java.util.Collection;
import java.util.Collections;

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

    private static class ExactLengthPredicate implements Predicate<String> {
        private int length;

        public ExactLengthPredicate(int length) {
            this.length = length;
        }

        @Override
        public boolean predict(String s) {
            return false;
        }
    }

    private static String filterExactLength(String[] parts, int length) {
        return Queriable.create(parts)
                .filter(new ExactLengthPredicate(length)).firstOrNull()
                .execute();
    }

    public static LicensePlate ParseLicensePlate(String s) {
        String[] parts =
                Queriable.create(s.split("\\D"))
                .filter(new Predicate<String>() {
                    @Override
                    public boolean predict(String s) {
                        return s.length() > 0;
                    }
                }).execute().toArray(new String[0]);

        String left = "";
        String right = "";
        String national = "";
        String type = "";

        if (parts.length == 2) {
            if (parts[0].length() == 2) {
                left = parts[0];

                if (parts[1].length() == 3) {
                    right = parts[1];
                    national = "00";
                }
                else if (parts[1].length() == 5) {
                    right = parts[1].substring(0, 3);
                    national = parts[1].substring(3);
                }
            }
            else if (parts[0].length() == 3) {
                right = parts[0];

                if (parts[1].length() == 2) {
                    left = parts[1];
                    national = "00";
                }
                else if (parts[1].length() == 4) {
                    left = parts[1].substring(0, 2);
                    national = parts[1].substring(2);
                }
            }
            else if (parts[0].length() == 4) {
                left = parts[0].substring(0, 2);
                national = parts[0].substring(2);

                if (parts[1].length() == 3) {
                    right = parts[1];
                }
            }
            else if (parts[0].length() == 5) {
                right = parts[0].substring(0, 3);
                national = parts[0].substring(3);

                if (parts[0].length() == 2) {
                    left = parts[0];
                }
            }
        }
        else if (parts.length == 3) {
            if (parts[0].length() == 2 && parts[1].length() == 3 && parts[2].length() == 2) {
                national = parts[0];
                right = parts[1];
                left = parts[2];
            }
        }

        parts = Queriable.create(s.split("\\d"))
                .map(new Selector<String, String>() {
                    @Override
                    public String select(String s) {
                        return s.trim();
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean predict(String s) {
                        return s.length() > 0;
                    }
                }).execute().toArray(new String[0]);

        if (parts.length == 1 && parts[0].length() == 1) {
            type = parts[0];
        }

        if (left.length() != 2 || right.length() != 3 || national.length() != 2 || type.length() != 1) {
            throw new IllegalArgumentException("invalid format");
        }

        return new LicensePlate(Integer.parseInt(left), type.charAt(0),
                Integer.parseInt(right), Integer.parseInt(national));
    }
}
