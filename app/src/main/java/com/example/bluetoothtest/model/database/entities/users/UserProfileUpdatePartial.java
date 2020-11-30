package com.example.bluetoothtest.model.database.entities.users;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"name", "parent"})
public class UserProfileUpdatePartial {

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "profile_path")
    public String profilePath;

    @NonNull
    @ColumnInfo(name = "parent")
    public String parent;

    public UserProfileUpdatePartial(@NonNull String name, String profilePath, @NonNull String parent) {
        this.name = name;
        this.profilePath = profilePath;
        this.parent = parent;
    }
}
