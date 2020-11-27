package com.example.bluetoothtest.model.entities.users;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserProfileUpdatePartial {

    @PrimaryKey
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "profile_path")
    public String profilePath;

    public UserProfileUpdatePartial(String name, String profilePath) {
        this.name = name;
        this.profilePath = profilePath;
    }
}
