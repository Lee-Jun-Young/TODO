package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv_today;
    private ImageView iv_settins;
    private Button btn_addTodo;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_today = findViewById(R.id.tv_today);
        iv_settins = findViewById(R.id.iv_settins);
        btn_addTodo = findViewById(R.id.btn_addTodo);

        Date date = new Date();
        String time = dateFormat.format(date);
        tv_today.setText(time);

        iv_settins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn_addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddtodoActivity.class);
                startActivity(intent);
            }
        });

    }

}
