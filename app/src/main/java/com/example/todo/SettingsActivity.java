package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    private ImageView iv_before;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        iv_before = findViewById(R.id.iv_before);


        iv_before.setOnClickListener(view -> finish());

    }
}
