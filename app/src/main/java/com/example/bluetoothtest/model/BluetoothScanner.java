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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BluetoothScanner {

    private List<BluetoothDevice> list;

    private final Set<BluetoothDevice> setDevices;

    private boolean isScanning = false;

    private final Handler searchHandler;

    private final Application application;

    private static final int THREAD_NUMBER = 1;

    public static final ExecutorService executorService =
            Executors.newFixedThreadPool(THREAD_NUMBER);

    private static final String TAG = "BluetoothScanner";

    public BluetoothScanner(Application application) {
        setDevices = new HashSet<>();

        searchHandler = new Handler(application.getMainLooper());

        list = new ArrayList<>();

        this.application = application;

    }


    private final ScanCallback searchCallBack = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            setDevices.add(result.getDevice());
            Log.i(TAG, "onScanResult: Device name : " + result.getDevice().getName());
        }
    };

    public void startScanProcess(BluetoothLeScanner scanner) {

        if (!isScanning) {
            searchHandler.postDelayed(() -> {
                stopScan(scanner);
                list.addAll(setDevices);
                Log.i(TAG, "after search : devices size : " + list.size());
            }, 1700);

            startScan(scanner);
        } else
            stopScan(scanner);
    }

    public List<BluetoothDevice> getDevices() {
        return list;
    }

    private void startScan(BluetoothLeScanner scanner) {
        scanner.startScan(searchCallBack);
        isScanning = true;
    }

    private void stopScan(BluetoothLeScanner scanner) {
        scanner.stopScan(searchCallBack);
        isScanning = false;
    }


}

