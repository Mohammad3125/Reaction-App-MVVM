package com.example.bluetoothtest.utility;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class WindowSetting implements View.OnSystemUiVisibilityChangeListener {
    public static final int BUILD_VERSION = Build.VERSION.SDK_INT;

    private Window window;

    public WindowSetting(Window window) {
        this.window = window;
    }

    public WindowSetting setStatusBarColor(int color) {
        window.setStatusBarColor(color);
        return this;
    }

    public WindowSetting hideStatusBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        return this;

    }

    public WindowSetting windowsFullScreen() {
        if (BUILD_VERSION >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        }
        return this;
    }

    @Override
    public void onSystemUiVisibilityChange(int i) {
        if (BUILD_VERSION >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);// Hiding Android Navigation Bar
        }


    }

    public WindowSetting setTransitionOverlap(boolean state) {
        window.setAllowEnterTransitionOverlap(state);
        return this;
    }
}
