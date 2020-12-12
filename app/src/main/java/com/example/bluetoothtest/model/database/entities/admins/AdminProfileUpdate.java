package com.example.bluetoothtest.model.database.entities.admins;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class AdminProfileUpdate {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "profile_path")
    @Nullable
    public String profilePath;


    public AdminProfileUpdate(@NotNull String name, @Nullable String profilePath) {
        this.name = name;
        this.profilePath = profilePath;
    }
}
