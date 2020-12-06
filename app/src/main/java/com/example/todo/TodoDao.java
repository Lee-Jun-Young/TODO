package com.example.todo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TodoDao {

    @Insert(onConflict = REPLACE)
    void insert(TodoList todoList);

    @Query("UPDATE todoList SET todo_content = :uText WHERE id = :uID")
    void update(int uID, String uText);

    @Delete
    void delete(TodoList todoList);

    @Query("SELECT * FROM todoList")
    List<TodoList> getAll();


}
