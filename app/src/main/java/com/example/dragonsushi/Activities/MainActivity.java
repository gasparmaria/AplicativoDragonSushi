package com.example.dragonsushi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.dragonsushi.R;

public class MainActivity extends AppCompatActivity {
    ImageButton home, cart, search, person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_menu);

        home = findViewById(R.id.menu_home);
        cart = findViewById(R.id.menu_cart);
        search = findViewById(R.id.menu_search);
        person = findViewById(R.id.menu_person);

        home.setOnClickListener(v ->{
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
        });
        search.setOnClickListener(v ->{
            Intent intent = new Intent(this,BuscaActivity.class);
            startActivity(intent);
        });
    }
}