package com.example.bluetoothtest.utility;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;

public class BleHelper {
    public BluetoothAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    public BluetoothManager getManager() {
        return manager;
    }

    public void setManager(BluetoothManager manager) {
        this.manager = manager;
    }

    public BluetoothLeScanner getBleScanner() {
        return BleScanner;
    }

    public void setBleScanner(BluetoothLeScanner bleScanner) {
        BleScanner = bleScanner;
    }

    public BluetoothAdapter adapter;
    public BluetoothManager manager;
    public BluetoothLeScanner BleScanner;


}
