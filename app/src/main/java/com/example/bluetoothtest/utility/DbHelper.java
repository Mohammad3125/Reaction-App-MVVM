package com.example.bluetoothtest.utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.bluetoothtest.model.entities.users.User;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    public static final String DB_NAME = "APP_DATABASE";
    public static final String TABLE_NAME = "USER_TABLE";
    public static final String TABLE_USERS_NAME = "USERS_TABLE";
    private static final String TAG = "DATABASE";
    private static final String CREATE_TABLE_CMD =
            "CREATE TABLE " +
                    TABLE_NAME +
                    "(PERSON_NAME varchar(24)," +
                    "PERSON_PASSWORD varchar(24)," +
                    "PERSON_REACT_TIME int," +
                    "PERSON_IMAGE_URI TEXT(500));";

    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " +
                    TABLE_USERS_NAME +
                    "(PERSON_NAME varchar(24)," +
                    "PARENT_NAME varchar(24)," +
                    "PERSON_REACT_TIME int," +
                    "PERSON_IMAGE_URI TEXT(500)," +
                    "IS_ADMIN BOOLEAN);";


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate: Creating Database");
        sqLiteDatabase.execSQL(CREATE_TABLE_CMD);
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertIntoUsers(User user, String table) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(makeInsertIntoCmd(table, user));
        database.close();
    }

    public Cursor getUsers(String table, String parentName) {
        return getReadableDatabase().rawQuery(makeQueryCmd(parentName, table), null);
    }


    public Cursor getParent(String table, String name) {

        return getReadableDatabase().rawQuery(makeParentQuery(table, name), null);
    }

    private String makeParentQuery(String table, String name) {
        return "SELECT * FROM " + table + " WHERE PERSON_NAME ='" + name + "';";
    }

    public static User getUser(Cursor cursor) {
        cursor.moveToFirst();
        return new User(cursor.getString(cursor.getColumnIndex("PERSON_NAME")),

                 cursor.getString(cursor.getColumnIndex("PERSON_IMAGE_URI")),
                cursor.getInt(cursor.getColumnIndex("PERSON_IMAGE_URI")));

    }

    public Cursor userExistenceChecker(String table, String name, String parentName) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(cmdCheckExistence(table, name, parentName), null);
    }

    public boolean parentExistenceChecker(String table, String name) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(cmdCheckParentExistence(table, name), null);
        return cursor.getCount() != 0;
    }

    public boolean parentExistenceChecker(String table, String name, String password) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(cmdCheckParentExistence(table, name, password), null);
        return cursor.getCount() != 0;
    }

    private String cmdCheckExistence(String table, String name, String parentName) {
        return "SELECT * FROM " + table + " WHERE PERSON_NAME = '" + name + "'" + "AND PARENT_NAME = '" + parentName + "';";
    }

    private String cmdCheckParentExistence(String table, String name) {
        return "SELECT * FROM " + table + " WHERE PERSON_NAME ='" + name + "';";
    }

    private String cmdCheckParentExistence(String table, String name, String password) {
        return "SELECT * FROM " + table + " WHERE PERSON_NAME ='" + name + "' AND PERSON_PASSWORD = '" + password + "';";
    }


    public User getParent(Cursor cursor) {
        cursor.moveToFirst();
        return new User(cursor.getString(cursor.getColumnIndex("PERSON_NAME")),
          cursor.getString(cursor.getColumnIndex("PERSON_IMAGE_URI")),
                cursor.getInt(cursor.getColumnIndex("PERSON_REACT_TIME")));

    }

    public List<User> getListFromCursor(Cursor cursor) {
        List<User> listUsers = new ArrayList<>();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            listUsers.add(new User(
                    cursor.getString(cursor.getColumnIndex("PERSON_NAME")),

                    cursor.getString(cursor.getColumnIndex("PERSON_IMAGE_URI")),
                    cursor.getInt(cursor.getColumnIndex("PERSON_REACT_TIME"))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        return listUsers;

    }

    public void editProfile(String path, String personName, String table) {
        getWritableDatabase().execSQL(createUpdateUserCmd(path, personName, table));
    }

    private String createUpdateUserCmd(String path, String personName, String table) {
        return "UPDATE " + table + " SET PERSON_IMAGE_URI = '" + path +
                "' WHERE PERSON_NAME = '" + personName + "';";

    }

    private String makeInsertIntoCmd(String tableName, User user) {
        return "INSERT INTO " + tableName + " VALUES " +
                "('" + user.name + "','" + "user.parentName "+ "','" + user.reactionTime + "','" + user.profilePath + "','" + "user.isAdmin" +
                "');";
    }

    private String makeInsertIntoCmdParent(String tableName, User user) {
        return "INSERT INTO " + tableName + " VALUES " +
                "('" + user.name + "','" + "user.password" + "','" + user.reactionTime + "','" + user.profilePath +
                "');";
    }


    private String makeQueryCmd(String parentName, String tableName) {
        return "SELECT * FROM " + tableName + " WHERE PARENT_NAME = " + "'" + parentName + "';";
    }


    public void removeRecord(String tableName, String personName) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(tableName, "PERSON_NAME" + " = ?", new String[]{personName});
        database.close();

    }

    private String makeDeleteCmd(String personName, String tableName) {
        return "DELETE FROM " + tableName + " WHERE PERSON_NAME= " + "'" + personName + "';";
    }

    public void insertIntoParent(User user, String table) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(makeInsertIntoCmdParent(table, user));
        database.close();
    }
}
