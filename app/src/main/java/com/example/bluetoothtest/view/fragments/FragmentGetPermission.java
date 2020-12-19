package com.example.bluetoothtest.view.fragments;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PermissionInfo;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

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
    LocationManager locationManager;

    Context context;

    @Override
    public void onStart() {
        super.onStart();

        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

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

        context = requireContext();

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
            //LOGIC IS REVERSED
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } else
                Toast.makeText(context, "Your location is already enabled", Toast.LENGTH_SHORT).show();

        } else if (id == cardViewBluetooth.getId()) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled())
                startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
            else
                Toast.makeText(context, "Your bluetooth is already enabled", Toast.LENGTH_SHORT).show();
        } else if (id == cardViewCamera.getId()) {
            if (permissionUtility.checkForPermission(Manifest.permission.CAMERA))
                permissionUtility.requestPermission(Manifest.permission.CAMERA, PermissionUtility.CAMERA_REQUEST_CODE);
            else
                Toast.makeText(context, "You have already granted camera access ", Toast.LENGTH_SHORT).show();
        }


    }
}
