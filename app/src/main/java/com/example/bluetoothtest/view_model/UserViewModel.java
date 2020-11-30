package com.example.bluetoothtest.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bluetoothtest.model.database.entities.users.User;
import com.example.bluetoothtest.repository.UsersRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    LiveData<List<User>> users;
    UsersRepository appRepository;
    Application application;

    public UserViewModel(@NonNull Application application) {
        super(application);

        this.application = application;

        appRepository = new UsersRepository(application);

    }

    public LiveData<List<User>> getUsers(String parentName) {
        return appRepository.getUsers(parentName);
    }

    public User getUser(String name, String parentName) {
        return appRepository.getUser(name, parentName);
    }

    public void insert(User user) {
        appRepository.insertToUser(user);
    }

    public void deleteAll() {
        appRepository.deleteAllUsers();
    }

    public void delete(User user) {
        appRepository.deleteFromUser(user);
    }

    public void update(String username, String profilePath, String parent) {
        appRepository.updateUser(username, profilePath, parent);
    }

    public boolean doesUserExists(String username, String parentName) {
        return appRepository.doesUserExist(username, parentName);
    }

}
