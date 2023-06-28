package com.example.findmyteam.user;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserDb.class},version=1, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();
    private static volatile UserDataBase INSTANCE;
    public static UserDataBase getInstance(Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (UserDataBase.class)
            {
                if(INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            UserDataBase.class,
                            "Utilizatori.db").build();

                }
            }
        }
        return INSTANCE;
    }
}