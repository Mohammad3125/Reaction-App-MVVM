package com.example.bluetoothtest.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bluetoothtest.model.entities.users.User;
import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.view.activities.MainActivity;
import com.example.bluetoothtest.utility.ProfileHelper;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.view_model.UserViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class FragmentEditProfile extends Fragment {

    //TODO
    // MIGRATING TWO TABLES INTO ONE

    private String profileBitmapPath;

    private String nameFile;

    private Bitmap finalBitmap;

    ProfileHelper uploaderHelper;

    ImageView profileImage;

    Button buttonDone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = view.findViewById(R.id.edit_profile_image_view);

        WindowSetting windowSetting = new WindowSetting(getActivity().getWindow());

        windowSetting.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorAccentG));

        FragmentEditProfileArgs arguments = FragmentEditProfileArgs.fromBundle(getArguments());

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        User user = userViewModel.getUser(arguments.getPersonName());

        profileBitmapPath = user.profilePath;

        String username = user.name;


        if (profileBitmapPath != null && !profileBitmapPath.isEmpty())
            ProfileHelper.
                    getImage(profileBitmapPath, profileImage);

        uploaderHelper =
                new ProfileHelper(getContext(), getActivity().getContentResolver());


        view.findViewById(R.id.edit_profile_button_cancel).
                setOnClickListener(this::popBack);


        profileImage.setOnClickListener(v -> startCameraIntent());

        buttonDone = view.findViewById(R.id.edit_profile_button_done);
        buttonDone.
                setOnClickListener(v -> {

                    ProfileHelper.delete(profileBitmapPath); //Deleting cache file

                    profileBitmapPath = uploaderHelper.uploadProfile
                            (nameFile, MainActivity.username, finalBitmap);

                    userViewModel.update(username, profileBitmapPath); // updating database

                    popBack(v);
                });

        view.findViewById(R.id.edit_profile_pick_image).
                setOnClickListener(v -> {
                    startCameraIntent();
                });


        view.findViewById(R.id.edit_profile_fab_edit_image).
                setOnClickListener(v -> {
                    startCameraIntent();
                });


    }

    private void popBack(View v) {
        Navigation.findNavController(v).navigateUp();
    }

    private void startCameraIntent() {

        //If we choose a pic from gallery it goes to the cache file
        // but if we abort that and again pic another image
        // that cache file will not be deleted and remains in cache directory
        // so we should delete every thing (if it's available) before picking new image
        // that static method will take care of null paths


        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON).setCropShape(CropImageView.CropShape.OVAL)
                // If we're on the fragment we should call 'getContext' instead of 'getActivity'
                // because if we call getContext , the onActivityResult of current class will be called
                // otherwise the MainActivity will be called and our algorithm won't work
                .start(getContext(), this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == Activity.RESULT_OK) {

                ProfileHelper.delete(profileBitmapPath); // Deleting previous picture

                buttonDone.setVisibility(View.VISIBLE);

                nameFile = "";

                Uri uri = result.getUri();

                finalBitmap = uploaderHelper.createBitmapFromUri(uri);

                UUID randomToken = UUID.randomUUID();

                nameFile = randomToken + ".png";

                profileBitmapPath = uploaderHelper.uploadAsCache(finalBitmap, nameFile);
                ProfileHelper.getImage(profileBitmapPath, profileImage);

            }

        }


    }
}
