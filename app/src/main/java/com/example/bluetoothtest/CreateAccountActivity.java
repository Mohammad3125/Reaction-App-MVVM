package com.example.bluetoothtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bluetoothtest.view.fragments.fragmentutility.DialogValidator;
import com.example.bluetoothtest.model.entities.admins.Admin;
import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.utility.PermissionUtility;
import com.example.bluetoothtest.utility.ProfileHelper;
import com.example.bluetoothtest.view_model.AdminViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.UUID;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {



    private static final int MINIMUM_LENGTH_TEXT = 3;
    private static final int MAXIMUM_LENGTH_TEXT = 24;

    private static final int MINIMUM_LENGTH_PASSWORD = 6;

    WindowSetting windowSetting;

    Button createButton;
    Button cancelButton;

    TextInputLayout usernameLayout;
    TextInputLayout passwordLayout;

    TextInputEditText usernameTextInput;
    TextInputEditText passwordTextInput;

    ImageView fromCamera;
    ImageView fromFile;
    ImageView userProfileImageView;


    Bitmap finalBitmap;

    ProfileHelper uploaderHelper;


    private String nameFile = "";
    private String profileBitmapPath = "";

    AdminViewModel adminViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_user);


        //Setting Window
        windowSetting = new WindowSetting(getWindow());
        windowSetting.windowsFullScreen().hideStatusBar().setTransitionOverlap(true);

        uploaderHelper = new ProfileHelper(this, getContentResolver());

        adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        initViews();

        initErrorWatcher();

        cancelButton.setOnClickListener(this);

        createButton.setOnClickListener(this);

        fromCamera.setOnClickListener(this);

    }

    private void initErrorWatcher() {
        passwordTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordLayout.setError(null); // Clearing Error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        usernameTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameLayout.setError(null); // Clearing Error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initViews() {
        cancelButton = findViewById(R.id.create_account_button_cancel);
        createButton = findViewById(R.id.create_account_button_create);

        usernameTextInput = findViewById(R.id.create_account_MaterialTextInputUsername);
        passwordTextInput = findViewById(R.id.create_account_MaterialTextInputPassword);

        usernameLayout = findViewById(R.id.create_account_MainLayout_EditText_Name);
        passwordLayout = findViewById(R.id.create_account_MainLayout_EditText_Password);

        fromCamera = findViewById(R.id.create_account_button_from_camera);

        userProfileImageView = findViewById(R.id.create_account_image_user_profile);


    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        //Animating Button
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_animation));

        //If Cancel Clicked Exit From Existing Activity
        if (viewId == cancelButton.getId()) {

            //Deleting Cache In Case We Didn't Create Account
            ProfileHelper.delete(profileBitmapPath);
            finish();
            return;
        }

        //Creating Account Logic
        if (viewId == createButton.getId()) {

            String usernameText = usernameTextInput.getText().toString().trim();
            String password = passwordTextInput.getText().toString().trim();


            if (!DialogValidator.isLengthValid(usernameText, MINIMUM_LENGTH_TEXT, MAXIMUM_LENGTH_TEXT)) {
                DialogValidator.setError("Your Name Is Not Valid", usernameLayout);
                return;
            }

            if (!DialogValidator.isLengthValid(password, MINIMUM_LENGTH_PASSWORD, MAXIMUM_LENGTH_TEXT)) {
                DialogValidator.setError("Your Password Is Not Valid", passwordLayout);
                return;
            }


            if (adminViewModel.doesAdminExist(usernameText)) {
                DialogValidator.setError("A User With That Username Exists", usernameLayout);
                return;
            }

            // Deleting Cache
            ProfileHelper.delete(profileBitmapPath);

            // Uploading Final Image
            profileBitmapPath = uploaderHelper.uploadProfile(nameFile, usernameText, finalBitmap);

            adminViewModel.insert(new Admin(usernameText,
                    password,
                    profileBitmapPath,
                    0));

            finish();
            return;
        }


        if (viewId == fromCamera.getId()) {
            PermissionUtility permissionUtility = new PermissionUtility(this, this);

            if (permissionUtility.checkForPermission(Manifest.permission.CAMERA)) {
                startCameraIntent();

            } else
                permissionUtility.requestPermission(Manifest.permission.CAMERA, PermissionUtility.CAMERA_REQUEST_CODE);

        }


    }

    private void startCameraIntent() {

        ProfileHelper.delete(profileBitmapPath);

        ProfileHelper.startCropImage(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ProfileHelper.delete(profileBitmapPath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                if (result == null) return;

                finalBitmap = uploaderHelper.createBitmapFromUri(result.getUri());

                UUID randomToken = UUID.randomUUID();

                nameFile = randomToken + ".png";

                profileBitmapPath = uploaderHelper.uploadAsCache(finalBitmap, nameFile);
                ProfileHelper.getImage(profileBitmapPath, userProfileImageView);

            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtility.CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent();
            } else
                Toast.makeText(this, "You Didn't Grant Access", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        windowSetting.onSystemUiVisibilityChange(newConfig.orientation);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        windowSetting.windowsFullScreen();

    }
}