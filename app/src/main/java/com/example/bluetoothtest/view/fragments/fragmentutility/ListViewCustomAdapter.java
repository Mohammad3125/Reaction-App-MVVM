package com.example.bluetoothtest.view.fragments.fragmentutility;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bluetoothtest.R;

import java.util.List;

public class ListViewCustomAdapter extends ArrayAdapter<ScanResult> {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bluetooth_listitem_view, parent, false);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_animation));
                }
            });

        ScanResult item = getItem(position);
        if (item != null) {

            TextView DeviceName, DeviceAddress, SignalTextView;
            Button Connect;
            DeviceName = convertView.findViewById(R.id.text_view_device_name);
            SignalTextView = convertView.findViewById(R.id.text_view_signal_power);
            DeviceAddress = convertView.findViewById(R.id.text_view_device_address);
            Connect = convertView.findViewById(R.id.button_connect_to_device);

            Connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_animation));
                }
            });

            SignalTextView.setText(String.valueOf(item.getRssi() + "dB"));
            DeviceName.setText(item.getDevice().getName());
            DeviceAddress.setText(item.getDevice().getAddress());


        }
        return convertView;


    }

    public ListViewCustomAdapter(@NonNull Context context, List<ScanResult> list) {

        super(context, 0, list);

    }
}
