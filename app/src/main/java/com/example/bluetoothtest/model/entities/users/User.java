package com.example.bluetoothtest.model.entities.users;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity(tableName = "users_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "profile_path")
    @Nullable
    public String profilePath;

    @ColumnInfo(name = "parent")
    public String parent;

    @ColumnInfo(name = "reaction_time")
    public int reactionTime;

    public User(@NotNull String name, @Nullable String profilePath, String parent, int reactionTime) {
        this.name = name;
        this.reactionTime = reactionTime;
        this.profilePath = profilePath;
        this.parent = parent;
    }


}
