package com.example.bluetoothtest.model;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bluetoothtest.model.entities.admins.Admin;
import com.example.bluetoothtest.model.entities.admins.AdminDAO;
import com.example.bluetoothtest.model.entities.users.User;
import com.example.bluetoothtest.model.entities.users.UserDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Admin.class}, version = 1)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    public abstract UserDAO userDOA();

    public abstract AdminDAO adminDOA();

    private static volatile AppDatabase INSTANCE;

    private static final String DATABASE_NAME = "user_db";

    public static final int THREAD_NUMBER = 4;
    public static final ExecutorService databaseExecutorService =
            Executors.newFixedThreadPool(THREAD_NUMBER);


    public static AppDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {

            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.
                            databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();


                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback databaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


        }
    };
}
