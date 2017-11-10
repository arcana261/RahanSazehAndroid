package com.example.arcana.rahansazeh.validation;

import android.widget.TextView;

/**
 * Created by arcana on 11/7/17.
 */

public class LengthValidator extends TextValidator {
    private final int minLength;
    private final int maxLength;

    public LengthValidator(TextView textView, int minLength, int maxLength) {
        super(textView, false);

        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public String validate(TextView textView, String text) {
        if (minLength == maxLength) {
            if (text.length() != minLength) {
                return "باید دقیقا " + minLength + " کاراکتر باشد";
            }
            else {
                return null;
            }
        }
        else if (text.length() < minLength) {
            return "باید حداقل " + minLength + " کاراکتر باشد";
        }
        else if (text.length() > maxLength) {
            return "باید حداکثر " + maxLength + " کاراکتر باشد";
        }
        else {
            return null;
        }
    }
}
