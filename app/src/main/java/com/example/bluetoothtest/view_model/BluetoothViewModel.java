package com.example.bluetoothtest.view_model;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bluetoothtest.repository.BluetoothRepository;

import java.util.List;
import java.util.prefs.AbstractPreferences;

public class BluetoothViewModel extends AndroidViewModel {

    BluetoothLeScanner scanner;
    BluetoothRepository repository;

    MutableLiveData<List<BluetoothDevice>> devices;

    public BluetoothViewModel(@NonNull Application application) {
        super(application);

        BluetoothManager manager = (BluetoothManager) application.getSystemService(Context.BLUETOOTH_SERVICE);

        repository = new BluetoothRepository(application);

        devices = new MutableLiveData<>();
    }


    public LiveData<List<BluetoothDevice>> getDevices() {
        repository.setOnDevicesReady(devices1 -> devices.setValue(devices1));
        return devices;
    }

    public void startScan() {

        repository.scan(BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner());
    }


}
