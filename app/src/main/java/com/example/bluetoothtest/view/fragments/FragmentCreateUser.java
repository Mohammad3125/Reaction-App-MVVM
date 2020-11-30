package com.example.bluetoothtest.view.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.model.database.entities.admins.Admin;
import com.example.bluetoothtest.model.database.entities.users.User;
import com.example.bluetoothtest.utility.PermissionUtility;
import com.example.bluetoothtest.utility.ProfileHelper;
import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.view.activities.MainActivity;
import com.example.bluetoothtest.view.fragments.fragmentutility.DialogValidator;
import com.example.bluetoothtest.view_model.AdminViewModel;
import com.example.bluetoothtest.view_model.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.UUID;

public class FragmentCreateUser extends Fragment implements View.OnClickListener {
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
    ImageView userProfileImageView;

    Context context;

    Bitmap finalBitmap;

    ProfileHelper uploaderHelper;

    boolean isAdmin;

    private String nameFile = "";
    private String profileBitmapPath = "";

    AdminViewModel adminViewModel;

    UserViewModel userViewModel;

    @Override
    public void onStart() {
        super.onStart();

        windowSetting = new WindowSetting(getActivity().getWindow());
        windowSetting.windowsFullScreen().
                setStatusBarColor(ContextCompat.getColor(context, R.color.colorAccentH));

        uploaderHelper = new ProfileHelper(getContext(), getActivity().getContentResolver());

        adminViewModel = new ViewModelProvider(requireActivity()).get(AdminViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);


        initErrorWatcher();

        cancelButton.setOnClickListener(this);

        createButton.setOnClickListener(this);

        fromCamera.setOnClickListener(this);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);


        isAdmin = FragmentCreateUserArgs.fromBundle(requireArguments()).getIsAdmin();


        initViews(view, isAdmin);

        context = getContext();

        ProfileHelper.getDefaultImage(userProfileImageView);

        return view;
    }

    private void initViews(View view, boolean isAdmin) {
        cancelButton = view.findViewById(R.id.create_account_button_cancel);
        createButton = view.findViewById(R.id.create_account_button_create);

        usernameTextInput = view.findViewById(R.id.create_account_MaterialTextInputUsername);
        passwordTextInput = view.findViewById(R.id.create_account_MaterialTextInputPassword);

        usernameLayout = view.findViewById(R.id.create_account_MainLayout_EditText_Name);
        passwordLayout = view.findViewById(R.id.create_account_MainLayout_EditText_Password);

        fromCamera = view.findViewById(R.id.create_account_button_from_camera);

        userProfileImageView = view.findViewById(R.id.create_account_image_user_profile);

        if (!isAdmin) {
            passwordLayout.setVisibility(View.INVISIBLE);
            passwordTextInput.setVisibility(View.INVISIBLE);

            //Moving button up a little bit
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) createButton.getLayoutParams();
            params.setMargins(0, -50, 0, 0);

        }

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        //Animating Button
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_animation));

        //If Cancel Clicked Exit From Existing Fragment
        if (viewId == cancelButton.getId()) {

            //Deleting Cache In Case We Didn't Create Account
            ProfileHelper.delete(profileBitmapPath);

            Navigation.findNavController(view).navigateUp();

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

            if (!DialogValidator.isLengthValid(password, MINIMUM_LENGTH_PASSWORD, MAXIMUM_LENGTH_TEXT) && isAdmin) {
                DialogValidator.setError("Your Password Is Not Valid", passwordLayout);
                return;
            }


            boolean error = isAdmin ? adminViewModel.doesAdminExist(usernameText) :
                    userViewModel.doesUserExists(usernameText, MainActivity.username);


            if (error) {
                DialogValidator.setError("A User With That Username Exists", usernameLayout);
                return;
            }


            // Deleting Cache
            ProfileHelper.delete(profileBitmapPath);

            // Uploading Final Image
            profileBitmapPath = uploaderHelper.uploadProfile(nameFile, usernameText, finalBitmap);

            if (isAdmin)
                adminViewModel.insert(new Admin(usernameText,
                        password,
                        profileBitmapPath,
                        0));

            else
                userViewModel.insert(new User(usernameText,
                        profileBitmapPath,
                        MainActivity.username,
                        0));

            Navigation.findNavController(view).navigateUp();


            return;
        }


        if (viewId == fromCamera.getId()) {
            PermissionUtility permissionUtility = new PermissionUtility(context, getActivity());

            if (permissionUtility.checkForPermission(Manifest.permission.CAMERA)) {
                startCameraIntent();

            } else
                permissionUtility.requestPermission(Manifest.permission.CAMERA, PermissionUtility.CAMERA_REQUEST_CODE);

        }
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

    private void startCameraIntent() {

        ProfileHelper.delete(profileBitmapPath);
        ProfileHelper.startCropImage(this, context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == Activity.RESULT_OK) {
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
                Toast.makeText(context, "You Didn't Grant Access", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        windowSetting.onSystemUiVisibilityChange(newConfig.orientation);
    }


}
