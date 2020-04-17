package com.bmsr.scaleheaderdemo.launchmodel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bmsr.scaleheaderdemo.R;

public class ActivityB extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Button btn = findViewById(R.id.btn);
        btn.setText("bbbb");
        btn.setOnClickListener(view-> {
            view.getContext().startActivity(new Intent(view.getContext(), ActivityC.class));
        });
        Toast.makeText(this,"测试页面---> bbbb",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this,"测试页面 onNewIntent ---> bbbb",Toast.LENGTH_LONG).show();
    }
}
