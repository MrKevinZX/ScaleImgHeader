package com.bmsr.scaleheaderdemo.launchmodel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bmsr.scaleheaderdemo.R;

public class ActivityD extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Button btn = findViewById(R.id.btn);
        btn.setText("dddd");
        btn.setOnClickListener(view-> {
            view.getContext().startActivity(new Intent(view.getContext(), ActivityB.class));
        });
        Toast.makeText(this,"测试页面---> dddd",Toast.LENGTH_LONG).show();
    }
}
