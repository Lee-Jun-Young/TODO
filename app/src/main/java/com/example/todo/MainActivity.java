package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static androidx.recyclerview.widget.RecyclerView.*;

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

        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        Date date = new Date();
        String time = dateFormat.format(date);
        tv_today.setText(time);

        database = TodoDatabase.getInstance(this);
        todoList = database.todoDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(todoList);

        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder target) {
                return false;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_edit)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                TodoList d = todoList.get(position);
                /*
                ItemTouchHelper.RIGHT, Todo update
                 */
                if(direction == ItemTouchHelper.RIGHT) {
                    int uID = d.getId();

                    String Text = d.getTodo_content();

                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dialog_update);

                    dialog.show();
                    dialog.setCancelable(false);
                    EditText et_editTodo = dialog.findViewById(R.id.et_editTodo);
                    Button btn_udpate = dialog.findViewById(R.id.btn_update);
                    Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                    et_editTodo.setText(Text);

                    et_editTodo.setOnEditorActionListener((textView, i, keyEvent) -> {
                        if (i == EditorInfo.IME_ACTION_DONE) {
                            dialog.dismiss();
                            String uText = et_editTodo.getText().toString().trim();
                            if (!uText.equals(" ")) {
                                database.todoDao().update(uID,uText);

                                todoList.clear();
                                todoList.addAll(database.todoDao().getAll());
                                adapter.notifyItemRemoved(position);
                                adapter.notifyItemRangeChanged(position, todoList.size());

                                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                            }
                        }
                        return true;
                    });

                    btn_udpate.setOnClickListener(view -> {
                        dialog.dismiss();
                        String uText = et_editTodo.getText().toString().trim();

                        database.todoDao().update(uID,uText);

                        todoList.clear();
                        todoList.addAll(database.todoDao().getAll());
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, todoList.size());

                        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    });

                    btn_cancel.setOnClickListener(view -> {
                        dialog.dismiss();
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, todoList.size());

                        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    });

                }
                /*
                ItemTouchHelper.LEFT, Todo delete
                 */
                else{
                    database.todoDao().delete(d);

                    todoList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, todoList.size());
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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

                        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                }
                 return true;
        });
    }
}