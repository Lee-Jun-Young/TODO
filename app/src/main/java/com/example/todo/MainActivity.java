package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv_today;
    EditText et_addTodo;
    Button btn_add;
    List<TodoList> todoList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    TodoDatabase database;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_view);
        et_addTodo = findViewById(R.id.et_addTodo);
        tv_today = findViewById(R.id.tv_today);
        btn_add = findViewById(R.id.btn_add);

        Date date = new Date();
        String time = dateFormat.format(date);
        tv_today.setText(time);

        database = TodoDatabase.getInstance(this);
        todoList = database.todoDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(MainActivity.this, todoList);

        recyclerView.setAdapter(adapter);

        btn_add.setOnClickListener(view -> {
            String str = et_addTodo.getText().toString().trim();

            if(!str.equals("")){
                TodoList data = new TodoList();
                data.setTodo_content(str);
                database.todoDao().insert(data);
                et_addTodo.setText("");

                todoList.clear();
                todoList.addAll(database.todoDao().getAll());
                adapter.notifyDataSetChanged();

            }
        });

    }
}