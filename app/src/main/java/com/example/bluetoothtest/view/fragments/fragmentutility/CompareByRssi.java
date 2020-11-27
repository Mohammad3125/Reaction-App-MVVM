package com.example.bluetoothtest.view.fragments.fragmentutility;

import android.bluetooth.le.ScanResult;

import java.util.Comparator;

public class CompareByRssi implements Comparator<ScanResult> {
    @Override
    public int compare(ScanResult scanResult, ScanResult t1) {

        return Integer.compare(scanResult.getRssi(), t1.getRssi());

    }
}
