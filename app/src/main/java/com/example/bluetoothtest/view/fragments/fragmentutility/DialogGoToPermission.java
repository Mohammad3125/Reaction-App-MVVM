package com.example.bluetoothtest.view.fragments.fragmentutility;

import android.app.Dialog;
import android.app.WallpaperInfo;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.utility.WindowSetting;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.zip.Inflater;

public class DialogGoToPermission extends DialogFragment {

    OnGivePermissionClicked onGivePermissionClicked;
    Window window;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = requireContext();

        View view = LayoutInflater.
                from(context).
                inflate(R.layout.layout_dialog_bluetooth_adapter_not_enabled, null, false);

        AlertDialog dialog =
                new MaterialAlertDialogBuilder(getContext(), R.style.dialogRoundCorners)
                        .setView(view).
                        create();


        view.findViewById(R.id.button_give_permission_dialog_permission).
                setOnClickListener(view2 -> onGivePermissionClicked.onClicked());

        view.findViewById(R.id.button_cancel_dialog_permission).
                setOnClickListener(view3 -> dialog.dismiss());

        window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        // It's not considered as best practice to show dialog right now
        // But we had to do that cause' we need to hide navigation bar
        dialog.show();

        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        window.
                clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        return dialog;
    }

    public void setOnGivePermissionClicked(OnGivePermissionClicked onGivePermissionClicked) {
        this.onGivePermissionClicked = onGivePermissionClicked;
    }

    public interface OnGivePermissionClicked {
        void onClicked();
    }
}
