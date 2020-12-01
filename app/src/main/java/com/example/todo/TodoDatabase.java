package com.example.todo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TodoList.class}, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase database;

    private static String DATABASE_NAME = "database";

    public synchronized static TodoDatabase getInstance(Context context){
        if(database == null){

            database = Room.databaseBuilder(context.getApplicationContext(),TodoDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return database;
    }

    public abstract TodoDao todoDao();

}