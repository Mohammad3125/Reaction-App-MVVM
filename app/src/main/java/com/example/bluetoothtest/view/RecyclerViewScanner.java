package com.example.bluetoothtest.view;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothtest.R;

public class RecyclerViewScanner extends ListAdapter<BluetoothDevice, RecyclerViewScanner.ViewHolder> {

    OnDeviceItemClickListener onDeviceItemClickListener;

    ViewHolder holder;

    public RecyclerViewScanner(@NonNull DiffUtil.ItemCallback<BluetoothDevice> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        holder = new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.bluetooth_listitem_view, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), onDeviceItemClickListener);
    }


     static class ViewHolder extends RecyclerView.ViewHolder {

        TextView deviceName;
        TextView deviceAddress;
        TextView devicePower;
        Button connect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.text_view_device_name);
            deviceAddress = itemView.findViewById(R.id.text_view_device_address);
            devicePower = itemView.findViewById(R.id.text_view_signal_power);

            connect = itemView.findViewById(R.id.button_connect_to_device);

        }

        public void bind(BluetoothDevice device, OnDeviceItemClickListener onDeviceItemClickListener) {
            deviceName.setText(device.getName());
            deviceAddress.setText(device.getAddress());
            devicePower.setText("Not Defined");

            connect.setOnClickListener(view -> onDeviceItemClickListener.onClick(device));
        }

    }

    public static class wordDiff extends DiffUtil.ItemCallback<BluetoothDevice> {


        @Override
        public boolean areItemsTheSame(@NonNull BluetoothDevice oldItem, @NonNull BluetoothDevice newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull BluetoothDevice oldItem, @NonNull BluetoothDevice newItem) {
            return oldItem.getAddress().equals(newItem.getAddress());
        }
    }


    public interface OnDeviceItemClickListener {
        void onClick(BluetoothDevice device);
    }

    public void setOnDeviceItemClickListener(OnDeviceItemClickListener onDeviceItemClickListener) {
        this.onDeviceItemClickListener = onDeviceItemClickListener;
    }


}


