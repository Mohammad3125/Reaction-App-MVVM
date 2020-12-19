package com.example.bluetoothtest.model.database.entities.admins;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AdminPasswordUpdatePartial {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "password")
    public String password;


    public AdminPasswordUpdatePartial(@NonNull String name, @NonNull String password) {
        this.name = name;
        this.password = password;
    }
}
