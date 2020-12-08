package com.example.bluetoothtest.model;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BluetoothScanner {

    private List<BluetoothDevice> list;

    private final Set<BluetoothDevice> setDevices;

    private final BluetoothLeScanner scanner;

    private boolean isScanning = false;

    private final Handler searchHandler;

    private final Application application;

    private static final String TAG = "BluetoothScanner";

    public BluetoothScanner(BluetoothLeScanner scanner, Application application) {
        this.scanner = scanner;

        setDevices = new HashSet<>();

        searchHandler = new Handler(application.getMainLooper());

        list = new ArrayList<>();

        this.application = application;

    }


    public List<BluetoothDevice> getDevices() {

        if (!isScanning) {

            searchHandler.postDelayed(() -> {
                stopScan();
                list.addAll(setDevices);
                Log.i(TAG, "after search : devices size : " + list.size());
            }, 1700);

            startScan();
        } else
            stopScan();


        return list;
    }


    private final ScanCallback searchCallBack = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            setDevices.add(result.getDevice());
            Log.i(TAG, "onScanResult: Devices" );
        }
    };

    private void startScan() {
        scanner.startScan(searchCallBack);
        isScanning = true;
    }

    private void stopScan() {
        scanner.stopScan(searchCallBack);
        isScanning = false;
    }


}

