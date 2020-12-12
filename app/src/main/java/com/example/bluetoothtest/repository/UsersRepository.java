package com.example.bluetoothtest.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.bluetoothtest.model.database.AppDatabase;
import com.example.bluetoothtest.model.database.entities.admins.Admin;
import com.example.bluetoothtest.model.database.entities.admins.AdminDAO;
import com.example.bluetoothtest.model.database.entities.admins.AdminProfileUpdate;
import com.example.bluetoothtest.model.database.entities.users.User;
import com.example.bluetoothtest.model.database.entities.users.UserDAO;
import com.example.bluetoothtest.model.database.entities.users.UserProfileUpdatePartial;

import java.util.List;

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

    public Admin getAdmin(String name) {
        return adminDAO.getAdmin(name);
    }

    public void updateAdmin(AdminProfileUpdate adminProfileUpdate) {
        AppDatabase.databaseExecutorService.execute(() ->
                adminDAO.update(adminProfileUpdate));

    }

    public boolean doesAdminExist(String name, String password) {
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


    public void updateUser(String username, String profilePath, String parent) {
        AppDatabase.databaseExecutorService.execute(() ->
                userDAO.update(new UserProfileUpdatePartial(username, profilePath, parent)));
    }

    public User getUser(String name, String parentName) {
        return userDAO.getUser(name, parentName);
    }

    public LiveData<List<User>> getUsers(String parentName) {
        return userDAO.getUsers(parentName);
    }

    public Boolean doesUserExist(String name, String parentName) {
        return userDAO.doesUserExist(name, parentName);
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
