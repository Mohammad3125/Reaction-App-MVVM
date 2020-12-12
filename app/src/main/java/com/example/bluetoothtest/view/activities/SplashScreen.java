package com.example.bluetoothtest.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.utility.WindowSetting;

public class SplashScreen extends AppCompatActivity {
    TextView textBlazePod;
    TextView textMoreansStudio;
    WindowSetting windowSetting;

    public static final String SPLASH_TIME_KEY = "time_splash_screen";

    public static final String SHARED_PREFERENCES_TAG = "tg-sharedPreference";
    public static final String LOGIN_STATE_KEY = "lg";
    public int splashScreenTime = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initViews();

        windowSetting = new WindowSetting(getWindow());
        windowSetting.windowsFullScreen().hideStatusBar();


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_TAG, MODE_PRIVATE);

        splashScreenTime = sharedPreferences.getInt(SPLASH_TIME_KEY, 3);

        new Handler().postDelayed(() -> {
            Class<?> myClass = MainActivity.class;

            if (!sharedPreferences.getBoolean(LOGIN_STATE_KEY, false))
                myClass = LoginHost.class;

            String name = sharedPreferences.getString(MainActivity.userTag, "default-user");
            startActivity(new Intent(this, myClass).putExtra("username", name));

            finish();
        }, splashScreenTime * 1000);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        startAnimations();
    }


    private void startAnimations() {
        textBlazePod.animate().alpha(1f).setDuration(500);
        textMoreansStudio.animate().alpha(1f).setDuration(500);
    }

    private void initViews() {
        textBlazePod = findViewById(R.id.TextViewBlazePod);
        textMoreansStudio = findViewById(R.id.TextViewMoreansStudio);
        setAlphas();

    }

    private void setAlphas() {
        textBlazePod.setAlpha(0f);
        textMoreansStudio.setAlpha(0);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        windowSetting.onSystemUiVisibilityChange(newConfig.orientation);
    }


}