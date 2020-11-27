package com.example.bluetoothtest.model.entities.admins;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "admins_table")
public class Admin {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "profile_path")
    @Nullable
    public String profilePath;

    @ColumnInfo(name = "reaction_time")
    public int reactionTime;

    public Admin(@NotNull String name, @NotNull String password, @Nullable String profilePath, int reactionTime) {
        this.name = name;
        this.reactionTime = reactionTime;
        this.password = password;
        this.profilePath = profilePath;
    }
}
