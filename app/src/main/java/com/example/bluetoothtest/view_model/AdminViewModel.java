package com.example.bluetoothtest.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bluetoothtest.model.entities.admins.Admin;
import com.example.bluetoothtest.repository.UsersRepository;

import java.util.List;

public class AdminViewModel extends AndroidViewModel {

    private UsersRepository repository;
    private LiveData<List<Admin>> admins;


    public AdminViewModel(@NonNull Application application) {
        super(application);

        repository = new UsersRepository(application);
        admins = repository.getAdmins();


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

    public boolean doesAdminExist(String username, String password) {
        return repository.doesAdminExist(username, password);
    }

    public boolean doesAdminExist(String username) {
        return repository.doesAdminExist(username);
    }

}
