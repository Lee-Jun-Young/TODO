package com.example.todo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(TodoList todo);

    @Update
    void update(TodoList todo);

    @Delete
    void delete(TodoList todo);

    @Query("SELECT * FROM todoList")
    LiveData<List<TodoList>> getAll();

    @Query("DELETE FROM todoList")
    void deleteAll();
}
