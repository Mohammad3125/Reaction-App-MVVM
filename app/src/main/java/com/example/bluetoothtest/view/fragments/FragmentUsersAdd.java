package com.example.bluetoothtest.view.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothtest.view.activities.MainActivity;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.model.entities.users.User;
import com.example.bluetoothtest.utility.PermissionUtility;
import com.example.bluetoothtest.utility.ProfileHelper;
import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.view.RecyclerViewAdapter;
import com.example.bluetoothtest.view.fragments.fragmentutility.DialogValidator;
import com.example.bluetoothtest.view_model.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.UUID;

public class FragmentUsersAdd extends Fragment {


    private static final int RESULT_OK = -1;


    MaterialButton buttonCreateUser;
    ImageView buttonFromCamera;
    ImageView userProfileImageView;
    View layoutDialog;
    ViewGroup container;
    Bitmap finalBitmap;
    String profileBitmapPath;
    String usernameText;
    ProfileHelper uploaderHelper;
    Context context;

    String nameFile = "";

    UserViewModel userViewModel;

    RecyclerView recyclerView;

    RecyclerViewAdapter adapter;

    private int rotationDegree = 0;

    private int counter = 0;

    private static final int BUILD_VERSION = Build.VERSION.SDK_INT;

    WindowSetting windowSetting;


    //TODO
    // LEARNING COORDINATOR LAYOUT AND SCROLLING TECHNIQUES IN FOLLOWING LINK
    // https://code.tutsplus.com/articles/scrolling-techniques-for-material-design--cms-24435


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        configRecyclerView(context);

        adapter.setRemoveListener(user -> userViewModel.delete(user));

        adapter.setItemListener((position, username) -> {
            NavDirections action = FragmentUsersAddDirections.
                    actionFragmentUsersAdd2ToFragmentUserInformation
                            (username);

            Navigation.findNavController(view).navigate(action);
        });


        userViewModel.getUsers(MainActivity.username).observe(getViewLifecycleOwner(), users -> adapter.submitList(users));


        buttonCreateUser.setOnClickListener(view2 ->
        {
            Navigation.findNavController(view2).
                    navigate(FragmentUsersAddDirections.actionFUserAddToFragmentCreateUser2(false));
        });

        /*buttonCreateUser.setOnClickListener(buttonView -> {

        layoutDialog = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_layout, container, false);

        AlertDialog dialog = new AlertDialog.Builder(context).setView(layoutDialog).create();

        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();


        DisplayMetrics metric = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);

        int displayHeight = metric.heightPixels;
        int displayWidth = metric.widthPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        layoutParams.width = (int) (displayWidth * 0.75f);
        layoutParams.height = (int) (displayHeight * 0.75f);

        dialog.getWindow().setAttributes(layoutParams);


        dialog.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dialog.getWindow().
                clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        userProfileImageView = layoutDialog.findViewById(R.id.image_user_profile);

        buttonFromCamera = layoutDialog.findViewById(R.id.button_from_camera);

        buttonFromCamera.setOnClickListener(view13 -> {

            PermissionUtility permissionUtility = new PermissionUtility(getContext(), getActivity());

            if (permissionUtility.checkForPermission(Manifest.permission.CAMERA))
                startCameraIntent();
            else
                permissionUtility.
                        requestPermission(Manifest.permission.CAMERA, PermissionUtility.CAMERA_REQUEST_CODE);
        });


        (layoutDialog.findViewById(R.id.dialog_button_cancel)).setOnClickListener(view1 -> {
            ProfileHelper.delete(profileBitmapPath);
            dialog.cancel();
        });


        (layoutDialog.findViewById(R.id.dialog_button_createuser))
                .setOnClickListener(view12 -> {
                    TextInputEditText editText = layoutDialog.findViewById(R.id.dialog_username_edittext);
                    TextInputLayout layoutEditText = layoutDialog.findViewById(R.id.dialog_username_layout_edittext);

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            layoutEditText.setError(null);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    usernameText = editText.getText().toString().trim();
                    if (!DialogValidator.isLengthValid(usernameText, 3, 24))
                        layoutEditText.setError("Your Name Is Not Valid");

                    else if (userViewModel.doesUserExists(usernameText))
                        layoutEditText.setError("Another User With That Name Exist");

                    else {

                        ProfileHelper.delete(profileBitmapPath);

                        profileBitmapPath = uploaderHelper.uploadProfile(nameFile, MainActivity.username, finalBitmap);

                        userViewModel.insert(new User(usernameText, profileBitmapPath, MainActivity.username, 0));

                        finalBitmap = null;

                        dialog.cancel();

                    }
                });
    });*/




    /* User item = listUsers.get(i);

     */

}

    private void startCameraIntent() {

        //If we choose a pic from gallery it goes to the cache file
        // but if we abort that and again pic another image
        // that cache file will not be deleted and remains in cache directory
        // so we should delete every thing (if it's available) before picking new image
        // that static method will take care of null paths
        ProfileHelper.delete(profileBitmapPath);

        //for being able to start CropImage from a fragment we should call the method differently
        ProfileHelper.startCropImage(this, context);
    }

    @Override
    public void onStart() {
        super.onStart();
        uploaderHelper =
                new ProfileHelper(context, getActivity().getContentResolver());

        windowSetting = new WindowSetting(getActivity().getWindow()).
                setStatusBarColor(ContextCompat.getColor(context, R.color.colorAccentH));

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        context = view.getContext();
        return view;

    }


    private void configRecyclerView(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerViewAdapter(new RecyclerViewAdapter.wordDiff());

        recyclerView.setAdapter(adapter);


    }


    private void initViews(View view) {
        buttonCreateUser = view.findViewById(R.id.Button_Add_Users);
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();

                finalBitmap = uploaderHelper.createBitmapFromUri(uri);

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
                Toast.makeText(getContext(), "You Didn't Grant Access", Toast.LENGTH_SHORT).show();
        }
    }




       /* User createdUser = new User(usernameText, MainActivity.username, false, profileBitmapPath, 100 + new Random().nextInt(500));

        dbHelper.insertIntoUsers(createdUser, DbHelper.TABLE_USERS_NAME);

        listUsers.add(createdUser);

        adapter.notifyDataSetChanged();

        if (profileBitmapPath != null)
            ProfileUploaderHelper.getImage(profileBitmapPath, userProfileImageView);


        imageData = null;
        profileBitmapPath = null;
        rotationDegree = 0;
        finalBitmap = null;*/


}

