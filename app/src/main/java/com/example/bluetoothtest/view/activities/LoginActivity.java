package com.example.bluetoothtest.view.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.view.fragments.fragmentutility.DialogValidator;
import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.view_model.AdminViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private static final int MINIMUM_LENGTH_TEXT = 3;
    private static final int MAXIMUM_LENGTH_TEXT = 24;

    private static final int MINIMUM_LENGTH_PASSWORD = 6;


    private static final int BUILD_VERSION = Build.VERSION.SDK_INT;

    TextInputEditText textInputUsername, textInputPassword;
    TextView enterAsGuestText, createAnAccountText, appName;
    Button enterButton;
    TextInputLayout layoutInputTextName, layoutInputTextPassword;

    AdminViewModel adminViewModel;


    WindowSetting windowSetting;
    // Finals

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);


        initViews();

        adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        windowSetting = new WindowSetting(getWindow());
        windowSetting.windowsFullScreen().hideStatusBar().setTransitionOverlap(true);

        // ViewCompat.setTransitionName(imageApp, IMAGE_VIEW_PAIR_ID);
        // ViewCompat.setTransitionName(appName, TEXT_VIEW_PAIR_ID);


        //In order to navigation from activity to fragment in another Activity beside
        // MainActivity which hosts navController we should make a graph independently for this activity
        createAnAccountText.setOnClickListener(view -> {
          //  Navigation.findNavController(view)
                 //   .navigate(MainActivityDirections.actionMainActivityToLoginHost());
        });

        enterButton.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.button_animation));


            String nameText = textInputUsername.getText().toString().trim();
            String passText = textInputPassword.getText().toString().trim();

            if (!DialogValidator.isLengthValid(nameText, MINIMUM_LENGTH_TEXT, MAXIMUM_LENGTH_TEXT)) {
                DialogValidator.setError("Your Name Is Not Valid", layoutInputTextName);
                return;
            }

            if (!DialogValidator.isLengthValid(passText, MINIMUM_LENGTH_PASSWORD, MAXIMUM_LENGTH_TEXT)) {
                DialogValidator.setError("Your Password Is Not Valid", layoutInputTextPassword);
                return;
            }

            if (!adminViewModel.doesAdminExist(nameText, passText)) {
                DialogValidator.setError("The Username Doesn't Exist ", layoutInputTextName);
                return;
            }


            AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                    .setView(R.layout.dialog_welcome_layout)
                    .create();

            dialog.show();

            dialog.findViewById(R.id.image_view_check_drawable).startAnimation
                    (AnimationUtils.loadAnimation(dialog.getContext(), R.anim.anim_rotate));

            SharedPreferences sharedPreferences = getSharedPreferences(SplashScreen.SHARED_PREFERENCES_TAG, MODE_PRIVATE);


            sharedPreferences.edit().putString(MainActivity.userTag, nameText).apply();


            sharedPreferences.edit().putBoolean(SplashScreen.LOGIN_STATE_KEY, true).apply();


            new Handler(getMainLooper()).postDelayed(() -> {
                startActivity(new Intent(LoginActivity.this, MainActivity.class).
                        putExtra("UserName", nameText));
                finish();
            }, 1000);


        });


        clearTextWatcher();

    }

    private void clearTextInputs() {
        textInputUsername.setText("");
        textInputPassword.setText("");
    }


    private void clearTextWatcher() {
        textInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layoutInputTextPassword.setError(null); // Clearing Error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        textInputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layoutInputTextName.setError(null); // Clearing Error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void initViews() {
        layoutInputTextName = findViewById(R.id.MainLayout_EditText_Name);
        layoutInputTextPassword = findViewById(R.id.MainLayout_EditText_Password);
        textInputPassword = findViewById(R.id.MaterialTextInputPassword);
        textInputUsername = findViewById(R.id.MaterialTextInputUsername);
        appName = findViewById(R.id.textview_appname_loginpage);
        enterButton = findViewById(R.id.Button_Enter);
        enterAsGuestText = findViewById(R.id.TextView_StartAsGuest);
        createAnAccountText = findViewById(R.id.TextView_Create_Account);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        textInputUsername.setText("");
        textInputPassword.setText("");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        windowSetting.onSystemUiVisibilityChange(newConfig.orientation);
    }
}