package com.example.bluetoothtest.view_model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bluetoothtest.model.database.entities.admins.Admin;
import com.example.bluetoothtest.model.database.entities.admins.AdminProfileUpdate;
import com.example.bluetoothtest.repository.UsersRepository;
import com.example.bluetoothtest.view.activities.MainActivity;
import com.example.bluetoothtest.view.activities.SplashScreen;

import java.util.List;

public class AdminViewModel extends AndroidViewModel {

    private UsersRepository repository;
    private LiveData<List<Admin>> admins;
    private final SharedPreferences sharedPreferences;

    public AdminViewModel(@NonNull Application application) {
        super(application);

        repository = new UsersRepository(application);
        admins = repository.getAdmins();
        sharedPreferences =
                application.getSharedPreferences(SplashScreen.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);


    }

    public void insert(Admin admin) {
        repository.insertToAdmin(admin);
    }

    public void deleteAll() {
        repository.deleteAllAdmins();
    }

    public void delete(Admin admin) {
        repository.deleteFromAdmin(admin);
    }

    public Admin getAdmin(String name) {
        return repository.getAdmin(name);
    }

    public void update(String name, String oldName, String profilePath) {
        MainActivity.username = name;
        sharedPreferences.edit().
                putString(MainActivity.userTag, name).
                apply();
        repository.updateAdmin(name, oldName, profilePath);
    }

    public void updatePassword(String name, String password) {
        repository.updateAdminPassword(name, password);
    }

    public boolean doesAdminExist(String username, String password) {
        return repository.doesAdminExist(username, password);
    }

    public boolean doesAdminExist(String username) {
        return repository.doesAdminExist(username);
    }


}
