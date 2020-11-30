package com.example.bluetoothtest.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.bluetoothtest.BuildConfig;
import com.example.bluetoothtest.view.activities.LoginActivity;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.view.activities.MainActivity;
import com.example.bluetoothtest.view.activities.SplashScreen;

public class FragmentSettingPreferences extends PreferenceFragmentCompat {

    Preference signOutPreference;
    Preference appVersionPreference;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting_page, rootKey);

        signOutPreference = findPreference("sign-out-button");
        signOutPreference.setOnPreferenceClickListener(preference -> {
            getContext().
                    getSharedPreferences(SplashScreen.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE).
                    edit().putBoolean(SplashScreen.LOGIN_STATE_KEY, false).apply();

            Navigation.findNavController(getView())
                    .navigate(FragmentSettingPreferencesDirections.
                            actionFSettingPreferencesToLoginHost());

            return false;
        });

        appVersionPreference = findPreference("app-version");
        if (appVersionPreference != null) {
            appVersionPreference.setDefaultValue(BuildConfig.VERSION_NAME);
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
