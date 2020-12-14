package com.example.bluetoothtest.view.fragments;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.utility.PermissionUtility;

public class FragmentGetPermission extends Fragment implements View.OnClickListener {

    CardView cardViewBluetooth;
    CardView cardViewLocation;
    CardView cardViewCamera;
    ImageView imageViewGetBack;

    PermissionUtility permissionUtility;

    @Override
    public void onStart() {
        super.onStart();

        imageViewGetBack.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());

        permissionUtility = new PermissionUtility(getContext(), requireActivity());

        cardViewBluetooth.setOnClickListener(this);

        cardViewLocation.setOnClickListener(this);

        cardViewCamera.setOnClickListener(this);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask_permision, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        cardViewBluetooth = view.findViewById(R.id.card_view_bluetooth_permission);
        cardViewLocation = view.findViewById(R.id.card_view_location_permission);
        cardViewCamera = view.findViewById(R.id.card_view_camera_permission);
        imageViewGetBack = view.findViewById(R.id.image_view_arrow_back);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.button_animation));
        int id = view.getId();
        if (id == cardViewLocation.getId()) {
            if (permissionUtility.checkForPermission(Manifest.permission.ACCESS_FINE_LOCATION))//LOGIC IS REVERSED
                permissionUtility.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, PermissionUtility.LOCATION_REQUEST_CODE);
        } else if (id == cardViewBluetooth.getId()) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled())
                startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
        } else if (id == cardViewCamera.getId()) {
            if (permissionUtility.checkForPermission(Manifest.permission.CAMERA))
                permissionUtility.requestPermission(Manifest.permission.CAMERA, PermissionUtility.CAMERA_REQUEST_CODE);
        }


    }
}
