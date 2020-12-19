package com.example.bluetoothtest.model.database.entities.admins;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AdminDAO {

    @Query("SELECT * FROM admins_table")
    LiveData<List<Admin>> getAdmins();

    @Query("SELECT * FROM admins_table WHERE name = :nameParameter")
    Admin getAdmin(String nameParameter);

    @Query("SELECT EXISTS(SELECT * FROM admins_table WHERE name =:username)")
    Boolean doesAdminExist(String username);

    @Query("SELECT EXISTS(SELECT * FROM admins_table WHERE name =:username AND password =:adminPassword)")
    Boolean doesAdminExist(String username, String adminPassword);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Admin admin);

    @Query("UPDATE admins_table SET name=:username,profile_path=:profilePath WHERE name = :oldName")
    void update(String username, String oldName, String profilePath);

    @Update(entity = Admin.class)
    void updatePassword(AdminPasswordUpdatePartial partial);

    @Delete
    void delete(Admin admin);

    @Query("DELETE FROM admins_table")
    void deleteAll();


}
