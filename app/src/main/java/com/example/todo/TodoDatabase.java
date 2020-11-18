package com.example.todo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TodoList.class}, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();

    private static volatile TodoDatabase INSTANCE;

    // database 객체를 매번 생성하는 건 리소스를 많이 사용하므로 싱글톤
    private static TodoDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TodoDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TodoDatabase.class, "todo_database").build();
                }
            }
        }
        return INSTANCE;
    }

    // DB 객체 제거
    public static void destroyInstance(){
        INSTANCE = null;
    }
}
