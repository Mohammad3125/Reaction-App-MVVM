package com.example.bluetoothtest.repository;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bluetoothtest.model.BluetoothScanner;

import java.util.List;

public class BluetoothRepository {

    private BluetoothScanner scannerModel;

    private Application application;

    private MutableLiveData<List<BluetoothDevice>> devices;

    public BluetoothRepository(Application application) {
        this.application = application;
        devices = new MutableLiveData<>();
    }

    public LiveData<List<BluetoothDevice>> getDevices() {
        return devices;
    }

    public void insertIntoAddedDevices(BluetoothDevice bluetoothDevice) {

    }

    public void scan(BluetoothLeScanner scanner) {
        if (scannerModel == null) scannerModel = new BluetoothScanner(scanner, application);

        scannerModel.startScanProcess();
    }


}
