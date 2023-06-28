package com.example.findmyteam.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class Users {
    @PrimaryKey(autoGenerate = true)
    public int userID;
    @ColumnInfo(name="First Name")
    public String firstName;
    @ColumnInfo(name="Last Name")
    public String lastName;
    @ColumnInfo(name="Email")
    public String email;
    @ColumnInfo(name="Password")
    public String password;
    public Users()
    {

    }
    public Users(String fn,String ln,String e,String p)
    {
        this.firstName=fn;
        this.lastName=ln;
        this.email=e;
        this.password=p;
    }
}

