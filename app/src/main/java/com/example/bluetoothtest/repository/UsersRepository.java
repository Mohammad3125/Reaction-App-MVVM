package com.example.bluetoothtest.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.bluetoothtest.model.AppDatabase;
import com.example.bluetoothtest.model.entities.admins.Admin;
import com.example.bluetoothtest.model.entities.admins.AdminDAO;
import com.example.bluetoothtest.model.entities.users.User;
import com.example.bluetoothtest.model.entities.users.UserDAO;
import com.example.bluetoothtest.model.entities.users.UserProfileUpdatePartial;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UsersRepository {
    public UserDAO userDAO;
    public AdminDAO adminDAO;

    Application application;

    LiveData<List<Admin>> admins;


    public UsersRepository(Application application) {
        this.application = application;

        AppDatabase appDatabase = AppDatabase.
                getINSTANCE(application);

        userDAO = appDatabase.userDOA();

        adminDAO = appDatabase.adminDOA();
        admins = adminDAO.getAdmins();

    }


    public LiveData<List<Admin>> getAdmins() {
        return admins;
    }

    public boolean doesAdminExist(String name, String password) {
        Toast.makeText(application, "I'm in getAdmin Method", Toast.LENGTH_SHORT).show();
        return adminDAO.doesAdminExist(name, password);
    }

    public boolean doesAdminExist(String username) {
        return adminDAO.doesAdminExist(username);
    }

    public void insertToAdmin(Admin admin) {
        AppDatabase.databaseExecutorService.execute(() -> adminDAO.insert(admin));
    }

    public void deleteFromAdmin(Admin admin) {
        AppDatabase.databaseExecutorService.execute(() -> adminDAO.delete(admin));
    }

    public void deleteAllAdmins() {
        adminDAO.deleteAll();
    }


    public void updateUser(String username, String profilePath) {
        AppDatabase.databaseExecutorService.execute(() ->
                userDAO.update(new UserProfileUpdatePartial(username, profilePath)));
    }

    public User getUser(String name) {
        return userDAO.getUser(name);
    }

    public LiveData<List<User>> getUsers(String parentName) {
        return userDAO.getUsers(parentName);
    }

    public Boolean doesUserExist(String name) {
        return userDAO.doesUserExist(name);
    }

    public void insertToUser(User user) {
        AppDatabase.databaseExecutorService.execute(() -> userDAO.insert(user));
    }

    public void deleteFromUser(User user) {
        AppDatabase.databaseExecutorService.execute(() -> userDAO.delete(user));
    }

    public void deleteAllUsers() {
        AppDatabase.databaseExecutorService.execute(() -> userDAO.deleteAll());
    }


}
