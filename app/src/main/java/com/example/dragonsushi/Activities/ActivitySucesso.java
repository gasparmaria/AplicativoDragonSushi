package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dragonsushi.R;

public class ActivitySucesso extends AppCompatActivity {
    TextView txtVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pedido_realizado);

        txtVoltar = findViewById(R.id.txtVoltar);

        txtVoltar.setOnClickListener(v ->{
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }
}