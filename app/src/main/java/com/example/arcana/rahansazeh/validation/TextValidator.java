package com.example.arcana.rahansazeh.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

/**
 * Created by arcana on 11/7/17.
 */

public abstract class TextValidator implements TextWatcher, View.OnFocusChangeListener {
    private final TextView textView;
    private final boolean validateAsTyped;
    private boolean hasError;

    public TextValidator(TextView textView, boolean validateAsTyped) {
        this.textView = textView;
        this.validateAsTyped = validateAsTyped;
        this.hasError = false;
    }

    public static void applyValidation(TextView textView, TextValidator validator) {
        textView.addTextChangedListener(validator);
        textView.setOnFocusChangeListener(validator);
    }

    public static TextValidator applyLength(TextView textView, int minLength, int maxLength) {
        TextValidator result = new LengthValidator(textView, minLength, maxLength);
        applyValidation(textView, result);
        return result;
    }

    public static TextValidator applyLength(TextView textView, int length) {
        return applyLength(textView, length, length);
    }

    public static TextValidator applyRegExp(TextView textView, String regExp, String message, boolean validateAsTyped) {
        TextValidator result = new RegExpValidator(textView, regExp, message, validateAsTyped);
        applyValidation(textView, result);
        return result;
    }

    public static TextValidator applyRegExp(TextView textView, String regExp, String message) {
        return applyRegExp(textView, regExp, message, false);
    }

    public static TextValidator applyRegExp(TextView textView, String regExp, boolean validateAsTyped) {
        return applyRegExp(textView, regExp, "ورودی باید مطابق با الگوی " + regExp + " باشد", validateAsTyped);
    }

    public static TextValidator applyRegExp(TextView textView, String regExp) {
        return applyRegExp(textView, regExp, false);
    }

    private void doValidate(boolean force) {
        if (textView.isEnabled()) {
            String text = textView.getText().toString();

            if (force || text.length() > 0) {
                String error = validate(textView, text);
                textView.setError(error);
                hasError = error != null;
            }
            else {
                clear();
            }
        }
        else {
            clear();
        }
    }

    public abstract String validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        if (validateAsTyped) {
            doValidate(true);
        }
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            doValidate(false);
        }
    }

    public boolean isValid() {
        doValidate(true);
        return !hasError;
    }

    public void clear() {
        textView.setError(null);
        hasError = false;
    }
}
