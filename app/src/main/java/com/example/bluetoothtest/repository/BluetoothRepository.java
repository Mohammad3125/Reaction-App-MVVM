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

    private BluetoothScanner scannerModel;

    private Application application;

    private MutableLiveData<List<BluetoothDevice>> devices;

    public BluetoothRepository(Application application) {
        this.application = application;
        devices = new MutableLiveData<>();
        scannerModel = new BluetoothScanner(application);
    }

    public LiveData<List<BluetoothDevice>> getDevices() {
        if (scannerModel == null) scannerModel = new BluetoothScanner(application);

        devices.setValue(scannerModel.getDevices());

        return devices;
    }

    public void insertIntoAddedDevices(BluetoothDevice bluetoothDevice) {

    }

    public void scan(BluetoothLeScanner scanner) {
        scannerModel.startScanProcess(scanner);
        //BluetoothScanner.executorService.execute(() -> scannerModel.startScanProcess(scanner));
    }


}
