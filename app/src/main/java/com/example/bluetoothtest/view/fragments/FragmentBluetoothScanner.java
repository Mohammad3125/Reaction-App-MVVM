package com.example.bluetoothtest.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.utility.ProfileHelper;
import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.view.RecyclerViewScanner;
import com.example.bluetoothtest.view_model.BluetoothViewModel;


public class FragmentBluetoothScanner extends Fragment {

    private static final String TAG = "FragmentBluetoothScan";


    RecyclerView recyclerViewNearbyDevices;
    RecyclerViewScanner recyclerViewNearbyAdapter;
    WindowSetting windowSetting;
    Context context;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerViewNearbyAdapter = new RecyclerViewScanner(new RecyclerViewScanner.wordDiff());

        recyclerViewNearbyDevices.setAdapter(recyclerViewNearbyAdapter);

        BluetoothViewModel bluetoothViewModel = new ViewModelProvider(requireActivity()).get(BluetoothViewModel.class);

        bluetoothViewModel.getDevices().
                observe(getViewLifecycleOwner(), devices -> recyclerViewNearbyAdapter.submitList(devices));


        recyclerViewNearbyAdapter.setOnDeviceItemClickListener(device -> {
            //onDeviceClicked
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_bluetooth_scan, container, false);

        InitViews(fragmentView);

        context = fragmentView.getContext();

        return fragmentView;

    }

    private void InitViews(View fragmentView) {
        recyclerViewNearbyDevices = fragmentView.findViewById(R.id.recycler_view_nearby_devices);

        recyclerViewNearbyDevices.setLayoutManager(new LinearLayoutManager(context));

        recyclerViewNearbyDevices.setHasFixedSize(true);


    }

    @Override
    public void onStart() {
        super.onStart();
        windowSetting = new WindowSetting(requireActivity().getWindow()).
                setStatusBarColor(ContextCompat.getColor(context, R.color.colorBackgroundDarker));

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

