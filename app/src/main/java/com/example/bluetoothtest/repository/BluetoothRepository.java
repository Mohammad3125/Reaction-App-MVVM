package com.example.bluetoothtest.repository;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanResult;
import android.os.Parcelable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bluetoothtest.model.BluetoothScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class BluetoothRepository {

    private final BluetoothScanner scannerModel;

    private Application application;

    List<BluetoothDevice> bluetoothDevices;

    BluetoothLeScanner scanner;

    public BluetoothRepository(Application application) {
        this.application = application;
        scannerModel = new BluetoothScanner(application);
        bluetoothDevices = new ArrayList<>();

    }

    public List<BluetoothDevice> getDevices() {
        scannerModel.setOnDeviceScanned(d -> {
            bluetoothDevices.addAll(d);
            scannerModel.startScanProcess(scanner);
        });
        return bluetoothDevices;
    }

    public void insertIntoAddedDevices(BluetoothDevice bluetoothDevice) {

    }

    public void scan(BluetoothLeScanner scanner) {
        scannerModel.startScanProcess(scanner);
        this.scanner = scanner;
        //BluetoothScanner.executorService.execute(() -> scannerModel.startScanProcess(scanner));
    }


}
