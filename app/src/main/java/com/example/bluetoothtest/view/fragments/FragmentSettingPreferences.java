package com.example.bluetoothtest.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bluetoothtest.BuildConfig;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.view.activities.MainActivity;
import com.example.bluetoothtest.view.activities.SplashScreen;
import com.example.bluetoothtest.view.fragments.fragmentutility.AlertDialogSplashTimePicker;

/*public class FragmentSettingPreferences extends PreferenceFragmentCompat {

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


}*/

public class FragmentSettingPreferences extends Fragment implements View.OnClickListener {
    private LinearLayout logoutLayout;
    private LinearLayout usersLayout;
    private LinearLayout profileLayout;
    private LinearLayout themeLayout;
    private LinearLayout startupTimeLayout;
    private LinearLayout permissionLayout;
    private TextView versionTextView;
    private TextView splashScreenTime;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        initViews(view);

        WindowSetting windowSetting = new WindowSetting(requireActivity().getWindow());

        windowSetting.windowsFullScreen().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorBackgroundDarker));

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().
                getSharedPreferences(SplashScreen.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);

        //setting app version into text view in setting fragment
        versionTextView.setText(BuildConfig.VERSION_NAME);
        splashScreenTime.setText(String.valueOf(sharedPreferences.getInt(SplashScreen.SPLASH_TIME_KEY, 3)));

        logoutLayout.setOnClickListener(this);
        usersLayout.setOnClickListener(this);
        startupTimeLayout.setOnClickListener(this);
        profileLayout.setOnClickListener(this);


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initViews(View view) {
        versionTextView = view.findViewById(R.id.setting_page_text_view_app_version);
        splashScreenTime = view.findViewById(R.id.setting_page_text_view_startup_time);
        logoutLayout = view.findViewById(R.id.setting_page_layout_logout);
        profileLayout = view.findViewById(R.id.setting_page_layout_profile);
        permissionLayout = view.findViewById(R.id.setting_page_layout_permission);
        startupTimeLayout = view.findViewById(R.id.setting_page_layout_startup_time);
        usersLayout = view.findViewById(R.id.setting_page_layout_users);
        themeLayout = view.findViewById(R.id.setting_page_layout_theme);
    }


    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_animation));

        if (view.getId() == logoutLayout.getId()) {
            getContext().
                    getSharedPreferences(SplashScreen.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE).
                    edit().putBoolean(SplashScreen.LOGIN_STATE_KEY, false).apply();

            Navigation.findNavController(view)
                    .navigate(FragmentSettingPreferencesDirections.
                            actionFSettingPreferencesToLoginHost());
        }

        if (view.getId() == usersLayout.getId())
            Navigation.findNavController(view).
                    navigate(FragmentSettingPreferencesDirections.
                            actionFSettingPreferencesToFUserAdd());


        if (view.getId() == startupTimeLayout.getId()) {
            AlertDialogSplashTimePicker dialogSplashTimePicker = new AlertDialogSplashTimePicker();

            dialogSplashTimePicker.
                    show(requireActivity().getSupportFragmentManager(), "splash_screen_number_picker_dialog");

            dialogSplashTimePicker.setOnNumberSlected(number ->
            {
                splashScreenTime.setText(String.valueOf(number));

                sharedPreferences.edit().
                        putInt(SplashScreen.SPLASH_TIME_KEY, number).
                        apply();
            });


        }

        if (view.getId() == profileLayout.getId()) {
            Navigation.findNavController(view).
                    navigate(FragmentSettingPreferencesDirections.
                            actionFSettingPreferencesToFEditProfile(MainActivity.username, true));

        }


    }


}
