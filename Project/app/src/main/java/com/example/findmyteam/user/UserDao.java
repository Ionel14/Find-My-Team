package com.example.findmyteam.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT *FROM user_table")
    List<Users> getAllUsers();
    @Insert
    void insertUser(Users user);
}
