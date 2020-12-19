package com.example.bluetoothtest.view.fragments.fragmentutility;

import com.google.android.material.textfield.TextInputLayout;

public class DialogValidator {

    public DialogValidator() {
    }

    public static boolean isLengthValid(String text, int min, int max) {
        int length = text.length();
        return length >= min && length <= max;
    }

    public static void setError(String error, TextInputLayout layout) {
        layout.setError(error);
    }

    public static void setError(String[] error, TextInputLayout[] layout) {
        for (int i = 0; i < layout.length; i++) {
            layout[i].setError(error[i]);
        }
    }

    public static boolean arePasswordsEqual(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

}
