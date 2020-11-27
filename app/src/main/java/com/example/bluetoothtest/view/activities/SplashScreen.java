package com.example.bluetoothtest.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.utility.WindowSetting;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    TextView textBlazePod;
    TextView textMoreansStudio;
    WindowSetting windowSetting;
    private final int TIME_SPLASH_SCREEN = 3;
    private final int ANIMATION_FADE_TIME = 250;
    private final int BUILD_VERSION = Build.VERSION.SDK_INT;
    private final int STATUS_BAR_COLOR_ID = R.color.colorBackgroundDarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initViews();
        setTransitions();

        windowSetting = new WindowSetting(getWindow());
        windowSetting.windowsFullScreen().hideStatusBar();


        //setStatusBarColor(STATUS_BAR_COLOR_ID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginPage = new Intent(SplashScreen.this, LoginActivity.class);
            /*    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        SplashScreen.this,

                        new Pair<>(logo, LoginActivity.IMAGE_VIEW_PAIR_ID),
                        new Pair<>(textBlazePod, LoginActivity.TEXT_VIEW_PAIR_ID)
                );
                //startActivity(loginPage, options.toBundle());*/

                startActivity(loginPage);
                finish();
            }
        }, TIME_SPLASH_SCREEN * 1000);

    }

    private void setTransitions() {
        //getWindow().setEnterTransition(new Explode());
        //getWindow().setExitTransition(new Explode());

        // Window window = getWindow();
        //window.setAllowEnterTransitionOverlap(true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        startAnimations();
    }


    private void fadeAnimations() {
        textBlazePod.animate().alpha(0f).setDuration(ANIMATION_FADE_TIME);
        textMoreansStudio.animate().alpha(0f).setDuration(ANIMATION_FADE_TIME);

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