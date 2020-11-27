package com.example.bluetoothtest.view.fragments.fragmentutility;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainBluetooth {

    private BluetoothAdapter adapter;
    private BluetoothManager manager;
    private BluetoothLeScanner scanner;
    private Context context;
    private Set<ScanResult> results;
    private List<ScanResult> Devices;
    private Handler scanHandler;
    private boolean isScanning = false;

    public MainBluetooth(Context context) {
        this.context = context;
        manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        results = new HashSet<>();
        scanHandler = new Handler(Looper.getMainLooper());
        Devices = new ArrayList<>();
        adapter = manager.getAdapter();
        scanner = adapter.getBluetoothLeScanner();

    }


    public void startScan(long period) {
      /*  if (isAdaptorEnabled()) {
            if (!isScanning) {
                scanHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isScanning = false;
                        scanner.stopScan(callback);
                    }
                }, period);
                Devices.clear();
                isScanning = true;
                scanner.startScan(callback);
            } else {
                isScanning = false;
                scanner.stopScan(callback);
            }
        }*/
    }

    public void startScan() {

        if (isAdaptorEnabled())
            startScan(2000);
    }

    private boolean isAdaptorEnabled() {
        return adapter.isEnabled() && adapter != null;
    }

    public List<ScanResult> getScanResults() {
        if (Devices == null) Devices = new ArrayList<>();
        return Devices;
    }


   /* private ScanCallback callback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            results.add(result);
            if (Devices.size() == 0) {
                Devices.add(result);
            } else {
                boolean same = false;
                for(int i = 0; i < Devices.size();i++)
                {
                    same = Devices.get(i).getDevice().getAddress().equals(NewDevice.getAddress());

                    if(same) {
                        break;

                    }
                }

                if(!same)
                {
                    Devices.add(new BluetoothDeviceModel(result.getRssi(), NewDevice));

                }

                adapter.notifyDataSetChanged();

            }


            //  BleHelpers.BleScanner.stopScan(this);
        };

        }       */
}

