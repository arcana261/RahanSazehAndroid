package com.example.arcana.rahansazeh.validation;

import android.widget.TextView;

import com.example.arcana.rahansazeh.utils.NationalCode;

/**
 * Created by arcana on 11/7/17.
 */

public class NationalCodeValidator extends TextValidator {
    public NationalCodeValidator(TextView textView, boolean validateAsTyped) {
        super(textView, validateAsTyped);
    }

    @Override
    public String validate(TextView textView, String text) {
        if (NationalCode.isValidIranianNationalCode(text)) {
            return null;
        }
        else {
            return "کد ملی را به صورت صحیح وارد نمایید";
        }
    }
}
