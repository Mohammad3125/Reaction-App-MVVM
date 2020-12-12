package com.example.bluetoothtest.view.fragments.fragmentutility;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bluetoothtest.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class AlertDialogSplashTimePicker extends DialogFragment {
    Context context;
    NumberPicker picker;
    Dialog dialog;
    onNumberSelected onNumberSelected;

    private int number;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        context = requireContext();

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.alertDialogStyle);


        View view = LayoutInflater.from(context).
                inflate(R.layout.dialog_splash_time_picker, null, false);

        builder.setView(view);

        dialog = builder.create();

        picker = view.findViewById(R.id.dialog_splash_screen_number_picker);

        picker.setMaxValue(5);
        picker.setMinValue(1);

        view.findViewById(R.id.button_dialog_splash_screen_done).
                setOnClickListener(view2 -> {
                    onNumberSelected.getNumber(picker.getValue());
                    dismiss();
                });


        return builder.create();
    }

    public void setOnNumberSlected(onNumberSelected onNumberSlected) {
        this.onNumberSelected = onNumberSlected;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        number = picker.getValue();

    }

    public interface onNumberSelected {
        void getNumber(int number);
    }


}
