package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddtodoActivity extends AppCompatActivity {

    private TextView tv_back;
    private TextView tv_add;
    private EditText et_addtodo;
    private EditText et_addmemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);

        tv_back = findViewById(R.id.tv_back);
        et_addtodo  = findViewById(R.id.et_addtodo);
        et_addmemo = findViewById(R.id.et_addmemo);
        tv_add = findViewById(R.id.tv_add);

        tv_back.setOnClickListener(view -> finish());

        tv_add.setOnClickListener(v -> {

        });

    }

    // 할 일 추가할 때 할 일이 빈칸인지 확인, 메모는 빈칸이어도 가능
    private boolean checkValues(EditText... args) {
        for (EditText e : args) {
            if (e.getText().length() == 0) {
                e.requestFocus();
                switch(e.getId()) {
                    case R.id.et_addtodo:
                        Toast.makeText(this, "할 일을 입력해 주세요..", Toast.LENGTH_SHORT).show();
                        return false;
                }
            }
        }
        return true;
    }

}
