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

        scanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();

        repository = new BluetoothRepository(application);

        if (devices == null) {
            devices = new MutableLiveData<List<BluetoothDevice>>();
        }
    }


    public LiveData<List<BluetoothDevice>> getDevices() {
        return repository.getDevices();
    }

    public void startScan() {
        repository.scan(scanner);
    }

}
