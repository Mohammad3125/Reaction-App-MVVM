package com.example.bluetoothtest.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.view.activities.MainActivity;
import com.example.bluetoothtest.view.activities.SplashScreen;
import com.example.bluetoothtest.view.fragments.fragmentutility.DialogValidator;
import com.example.bluetoothtest.view_model.AdminViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentLogin extends Fragment {
    private static final int MINIMUM_LENGTH_TEXT = 3;
    private static final int MAXIMUM_LENGTH_TEXT = 24;

    private static final int MINIMUM_LENGTH_PASSWORD = 6;


    private static final int BUILD_VERSION = Build.VERSION.SDK_INT;

    TextInputEditText textInputUsername, textInputPassword;
    TextView enterAsGuestText, createAnAccountText, appName;
    Button enterButton;
    TextInputLayout layoutInputTextName, layoutInputTextPassword;

    AdminViewModel adminViewModel;

    Context context;

    WindowSetting windowSetting;

    @Override
    public void onStart() {
        super.onStart();

        adminViewModel = new ViewModelProvider(requireActivity()).get(AdminViewModel.class);

        windowSetting = new WindowSetting(getActivity().getWindow());

        windowSetting.hideStatusBar().setTransitionOverlap(true);


        createAnAccountText.setOnClickListener(view -> {
            // Going To The Creating Account Fragment
            Navigation.findNavController(view).navigate(FragmentLoginDirections.
                    actionFragmentLoginToFragmentCreateUser(true));
            // false argument is defining that we're creating user or admin
        });

        enterButton.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_animation));


            String nameText = textInputUsername.getText().toString().trim();
            String passText = textInputPassword.getText().toString().trim();

            boolean Proceed = true;

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

            SharedPreferences sharedPreferences = context.getSharedPreferences(SplashScreen.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);


            sharedPreferences.edit().putString(MainActivity.userTag, nameText).apply();


            sharedPreferences.edit().putBoolean(SplashScreen.LOGIN_STATE_KEY, true).apply();


            new Handler(getActivity().getMainLooper()).postDelayed(() -> {
                //Intent StartApp = new Intent(context, MainActivity.class);
                //StartApp.putExtra("UserName", nameText);
                //startActivity(StartApp);
                Navigation.findNavController(view).
                        navigate(FragmentLoginDirections.actionFragmentLoginToMainActivity22(nameText));

            }, 1000);
        });


        clearTextWatcher();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);

        context = getContext();

        return view;

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

    private void initViews(View view) {
        layoutInputTextName = view.findViewById(R.id.MainLayout_EditText_Name);
        layoutInputTextPassword = view.findViewById(R.id.MainLayout_EditText_Password);
        textInputPassword = view.findViewById(R.id.MaterialTextInputPassword);
        textInputUsername = view.findViewById(R.id.MaterialTextInputUsername);
        appName = view.findViewById(R.id.textview_appname_loginpage);
        enterButton = view.findViewById(R.id.Button_Enter);
        enterAsGuestText = view.findViewById(R.id.TextView_StartAsGuest);
        createAnAccountText = view.findViewById(R.id.TextView_Create_Account);
    }
}
