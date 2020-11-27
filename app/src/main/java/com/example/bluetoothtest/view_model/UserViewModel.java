package com.example.bluetoothtest.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bluetoothtest.model.entities.users.User;
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

        users = appRepository.getUsers();


    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public User getUser(String name) {
        return appRepository.getUser(name);
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

    public void update(String username, String profilePath) {
        appRepository.updateUser(username, profilePath);
    }

    public boolean doesUserExists(String username) {
        return appRepository.doesUserExist(username);
    }

}
