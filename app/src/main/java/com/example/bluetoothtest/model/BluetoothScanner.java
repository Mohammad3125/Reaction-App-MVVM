package com.example.bluetoothtest.model;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BluetoothScanner {
    LiveData<List<BluetoothDevice>> list;


    public LiveData<List<BluetoothDevice>> getDevices(BluetoothLeScanner scanner) {
        return list;
    }

}

