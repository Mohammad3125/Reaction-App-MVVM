package com.example.bluetoothtest.view.fragments.fragmentutility;

import com.google.android.material.textfield.TextInputLayout;

public class DialogValidator {

    public DialogValidator() {
    }

    public static boolean isLengthValid(String usernameText, int min, int max) {
        int length = usernameText.length();
        return length >= min && length <= max;
    }

    public static void setError(String error, TextInputLayout layout) {
        layout.setError(error);
    }

}
