package com.example.arcana.rahansazeh.validation;

import android.widget.TextView;

/**
 * Created by arcana on 11/7/17.
 */

public class RegExpValidator extends TextValidator {
    private final String regExp;
    private final String message;

    public RegExpValidator(TextView textView, String regExp, String message, boolean validateAsTyped) {
        super(textView, validateAsTyped);

        this.regExp = regExp;
        this.message = message;
    }

    @Override
    public String validate(TextView textView, String text) {
        if (!text.matches(regExp)) {
            return message;
        }
        else {
            return null;
        }
    }
}
