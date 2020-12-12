package com.example.bluetoothtest.view.fragments;

import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bluetoothtest.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentBluetoothScanner extends Fragment {

    private static final String TAG = "FragmentBluetoothScan";
    ListView ScannedDeviceListView, ConnectedDeviceListView;
    TextView ConnectedDeviceTextView, FoundedDevicesTextView;
    Button ScanButton;

    List<ScanResult> scanResultList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_bluetooth_scan, container, false);
/*

        InitViews(fragmentView);

        startAnimateViews();

        scanResultList = new ArrayList<>();

        ScanButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_animation));

                Toast.makeText(getContext(), "Starting Scan", Toast.LENGTH_SHORT).show();

            }
        });*/


        return fragmentView;

    }

    private void startAnimateViews() {
     /*   ConnectedDeviceTextView.animate().translationY(1f).setDuration(360);
        ConnectedDeviceListView.animate().translationY(1f).setDuration(370);
        ScanButton.animate().translationY(1f).setDuration(380);
        ScannedDeviceListView.animate().translationY(1f).setDuration(390);
        FoundedDevicesTextView.animate().translationY(1f).setDuration(400);*/

    }

    private void InitViews(View fragmentView) {
     /*   ScannedDeviceListView = fragmentView.findViewById(R.id.Found_Devices_ListView);
        ConnectedDeviceListView = fragmentView.findViewById(R.id.Added_Devices_ListView);
        ConnectedDeviceTextView = fragmentView.findViewById(R.id.TextView_Connected_Device);
        FoundedDevicesTextView = fragmentView.findViewById(R.id.TextViewAddedDevice);
        ScanButton = fragmentView.findViewById(R.id.Button_Scan);*/
    }


  /* private void StartScan() {
        if (!isScanning) {
            ScanHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ScannerBluetooth.stopScan(scanCallback);
                    isScanning = false;


                }
            }, 5000);
            scanResultList.clear();
            ScannerBluetooth.startScan(scanCallback);
            isScanning = true;

        } else {
            ScannerBluetooth.stopScan(scanCallback);
            isScanning = false;
        }
    }
*/

 /*   private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);



            devices.add(result);

            scanResultList.addAll(devices);
            Collections.sort(scanResultList,new CompareByRssi());
            adapter.notifyDataSetChanged();


           /* if (Devices.size() == 0) {
                Devices.add(new BluetoothDeviceModel(result.getRssi(),NewDevice));
                Log.i(TAG, "onScanResult: Device Size Was 0 " + "Device Added = " + Devices.get(Devices.size() - 1).getDevice().getName());
            } else {
                Log.i(TAG, "onScanResult: Last Device Name " + Devices.get(Devices.size() - 1).getDevice().getName() + " New Result : " + NewDevice.getName());
                boolean same = false;
                for(int i = 0; i < Devices.size();i++)
                {
                    same = Devices.get(i).getDevice().getAddress().equals(NewDevice.getAddress());

                    if(same) {
                        Log.i(TAG, "onScanResult: Same At Index : " + i);
                        break;

                    }
                }

                Log.i(TAG, "onScanResult: Are they same ? " + ((same) ? " yes" : " no"));
                if(!same)
                {
                    Devices.add(new BluetoothDeviceModel(result.getRssi(), NewDevice));
                    Log.i(TAG, "onScanResult: They Wasn't Same Added");

                }
                Log.i(TAG, "onScanResult: Devices Size : " + Devices.size());

                adapter.notifyDataSetChanged();

            }


            //  BleHelpers.BleScanner.stopScan(this);
        }
    };*/


}

