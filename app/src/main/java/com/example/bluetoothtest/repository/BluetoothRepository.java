package com.example.bluetoothtest.repository;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;

import androidx.lifecycle.LiveData;

import com.example.bluetoothtest.model.BluetoothScanner;

import java.util.List;

public class BluetoothRepository {

    private BluetoothScanner scannerModel;

    private Application application;

    public BluetoothRepository(Application application) {
        this.application = application;
    }

    public List<BluetoothDevice> getDevices(BluetoothLeScanner scanner) {
        scannerModel = new BluetoothScanner(scanner, application);
        return scannerModel.getDevices();
    }

    public void insertIntoAddedDevices(BluetoothDevice bluetoothDevice) {

    }

}
