package com.example.bluetoothtest.repository;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;

import androidx.lifecycle.LiveData;

import com.example.bluetoothtest.model.BluetoothScanner;

import java.util.List;

public class BluetoothRepository {

    public BluetoothRepository(Application application) {


    }

    public List<BluetoothDevice> getDevices(BluetoothLeScanner scanner) {
        return new BluetoothScanner(scanner).getDevices();
    }

    public void insertIntoAddedDevices(BluetoothDevice bluetoothDevice) {

    }

}
