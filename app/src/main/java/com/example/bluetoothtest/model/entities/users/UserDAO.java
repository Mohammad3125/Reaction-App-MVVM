package com.example.bluetoothtest.model.entities.users;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {


    @Query("SELECT EXISTS(SELECT * FROM users_table WHERE name = :user_name)")
    Boolean doesUserExist(String user_name);

    @Query("SELECT * FROM users_table WHERE name = :username")
    User getUser(String username);

    @Query("SELECT * FROM users_table")
    LiveData<List<User>> getUsers();

    @Update(entity = User.class)
    void update(UserProfileUpdatePartial updatePartial);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users_table")
    void deleteAll();


}
