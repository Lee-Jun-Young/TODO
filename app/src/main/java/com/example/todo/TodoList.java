package com.example.todo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todoList")
public class TodoList {

    // Room에서 자동으로 id를 할당
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String todoName;
    private String todoMemo;

    public TodoList(String todoName, String todoMemo) {
        this.todoName = todoName;
        this.todoMemo = todoMemo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public String getTodoMemo() {
        return todoMemo;
    }

    public void setTodoMemo(String todoMemo) {
        this.todoMemo = todoMemo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TodoList{");
        sb.append("id=").append(id);
        sb.append(", todoName='").append(id);
        sb.append("id=").append(todoName).append('\'');
        sb.append(", todoMemo='").append(todoMemo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
