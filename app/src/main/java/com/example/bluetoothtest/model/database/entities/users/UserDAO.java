package com.example.bluetoothtest.model.database.entities.users;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {


    @Query("SELECT EXISTS(SELECT * FROM users_table WHERE name = :user_name AND parent = :parentName)")
    Boolean doesUserExist(String user_name, String parentName);

    @Query("SELECT * FROM users_table WHERE name = :username AND parent = :parentName")
    User getUser(String username, String parentName);

    @Query("SELECT * FROM users_table WHERE parent =:parentName")
    LiveData<List<User>> getUsers(String parentName);

    @Update(entity = User.class)
    void update(UserProfileUpdatePartial updatePartial);

    @Query("UPDATE users_table SET parent =:parentName WHERE parent= :oldParentName")
    void updateAllUsersParent(String parentName, String oldParentName);


    @Query("UPDATE users_table SET name=:username,profile_path=:profilePath WHERE parent = :parentName AND name=:oldUsername ")
    void updateUsername(String username, String oldUsername,String profilePath ,String parentName);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users_table")
    void deleteAll();


}
