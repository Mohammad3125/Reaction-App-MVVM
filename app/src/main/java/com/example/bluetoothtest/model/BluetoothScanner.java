package com.example.bluetoothtest.model;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Handler;

import androidx.lifecycle.LiveData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BluetoothScanner {

    private List<BluetoothDevice> list;

    private Set<BluetoothDevice> setDevices;

    private BluetoothLeScanner scanner;

    private boolean isScanning = false;

    private Handler searchHandler;

    public BluetoothScanner(BluetoothLeScanner scanner) {
        this.scanner = scanner;

        setDevices = new HashSet<>();

        searchHandler = new Handler();
    }


    public List<BluetoothDevice> getDevices() {

        if (!isScanning) {

            searchHandler.postDelayed(() -> {
                stopScan();
                list.clear();
                list.addAll(setDevices);
            }, 1700);

            startScan();
        } else
            stopScan();



        return list;
    }


    private ScanCallback searchCallBack = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            setDevices.add(result.getDevice());
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

