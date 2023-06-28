package com.example.findmyteam.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT *FROM user_table")
    List<UserDb> getAllUsers();
    @Query("Delete FROM user_table")
    void deleteAllUsers();
    @Insert
    void insertUser(UserDb user);
    @Query("Select *From user_table where webId Like :Id")
    public UserDb getUser(String Id);
}
