package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.SettingsSlicesContract;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv_today;
    private EditText et_addTodo;
    private ImageView iv_settins;
    private List<TodoList> todoList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private TodoDatabase database;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        recyclerView = findViewById(R.id.rv_view);
        et_addTodo = findViewById(R.id.et_addTodo);
        tv_today = findViewById(R.id.tv_today);
        iv_settins = findViewById(R.id.iv_settins);

        Date date = new Date();
        String time = dateFormat.format(date);
        tv_today.setText(time);

        database = TodoDatabase.getInstance(this);
        todoList = database.todoDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(MainActivity.this, todoList);

        recyclerView.setAdapter(adapter);

        iv_settins.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        et_addTodo.setOnEditorActionListener((textView, i, keyEvent) -> {
                 String str = et_addTodo.getText().toString().trim();

                 if (str.isEmpty()) {

                     Toast.makeText(getApplicationContext(), "할 일을 입력해주세요", Toast.LENGTH_SHORT).show();
                     textView.clearFocus();
                     textView.setFocusable(false);
                     textView.setFocusableInTouchMode(true);
                     textView.setFocusable(true);

                     assert inputMethodManager != null;
                     inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);

                     return true;
                 }

                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (!str.equals(" ")) {
                        TodoList data = new TodoList();
                        data.setTodo_content(str);
                        database.todoDao().insert(data);
                        et_addTodo.setText("");

                        todoList.clear();
                        todoList.addAll(database.todoDao().getAll());
                        adapter.notifyDataSetChanged();
                        textView.setFocusable(false);
                        textView.setFocusableInTouchMode(true);
                        textView.setFocusable(true);

                        assert inputMethodManager != null;
                        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                }
                 return true;
             });
    }
}