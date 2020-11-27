package com.example.bluetoothtest.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.bluetoothtest.BuildConfig;
import com.example.bluetoothtest.view.activities.LoginActivity;
import com.example.bluetoothtest.R;

public class FragmentSettingPreferences extends PreferenceFragmentCompat {

    Preference signOutPreference;
    Preference appVersionPreference;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting_page, rootKey);

        signOutPreference = findPreference("sign-out-button");
        signOutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent loginPage = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginPage);
                getActivity().finish();
                return false;
            }
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
