package com.example.findmyteam.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserDb {
    @PrimaryKey(autoGenerate = true)
    public int userID;
    @ColumnInfo(name="First Name")
    public String firstName;
    @ColumnInfo(name="Last Name")
    public String lastName;
    @ColumnInfo(name="Email")
    public String email;
    @ColumnInfo(name="WebId")
    public String webId;
    public UserDb()
    {

    }
    public UserDb(String fn, String ln, String e, String p)
    {
        this.firstName=fn;
        this.lastName=ln;
        this.email=e;
        this.webId=p;
    }
}

