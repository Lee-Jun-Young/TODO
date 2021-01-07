package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LauncherActivity extends AppCompatActivity {

    private ImageView iv_Launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Handler handler = new Handler();
        //postDelayed 메서드를 사용하기 위해선 Runnable 객체를 인자로 자정해야함
        handler.postDelayed(() -> {
            Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        },2000); //여기서 1500의 단위는 ms(밀리 세컨드)
    }
}