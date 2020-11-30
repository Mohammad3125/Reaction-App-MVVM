package com.example.bluetoothtest.model.database.entities.admins;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AdminDAO {

    @Query("SELECT * FROM admins_table")
    LiveData<List<Admin>> getAdmins();

    @Query("SELECT EXISTS(SELECT * FROM admins_table WHERE name =:username)")
    Boolean doesAdminExist(String username);

    @Query("SELECT EXISTS(SELECT * FROM admins_table WHERE name =:username AND password =:adminPassword)")
    Boolean doesAdminExist(String username, String adminPassword);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Admin admin);

    @Delete
    void delete(Admin admin);

    @Query("DELETE FROM admins_table")
    void deleteAll();


}
