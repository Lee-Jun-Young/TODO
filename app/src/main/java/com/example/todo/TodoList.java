package com.example.todo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "todoList")
public class TodoList implements Serializable {

    // Room에서 자동으로 id를 할당
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "todo_content")
    private String todo_content;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo_content() {
        return todo_content;
    }

    public void setTodo_content(String todo_content) {
        this.todo_content = todo_content;
    }
}