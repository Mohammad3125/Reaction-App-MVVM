package com.example.bluetoothtest.view.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bluetoothtest.R;

public class LoginHost extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_host_layout);


        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().
                        findFragmentById(R.id.login_nav_container);


        NavController navController = navHostFragment.getNavController();





    }
}
