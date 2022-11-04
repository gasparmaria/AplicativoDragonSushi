package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dragonsushi.R;

public class HomeActivity extends AppCompatActivity {
    ImageButton btnEntradas, btnPratosQuentes, btnTemakis, btnPecas, btnCombos, btnBebidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnEntradas = findViewById(R.id.btnEntradas);
        btnPratosQuentes = findViewById(R.id.btnPratosQuentes);
        btnTemakis = findViewById(R.id.btnTemakis);
        btnPecas = findViewById(R.id.btnPecas);
        btnCombos = findViewById(R.id.btnCombos);
        btnBebidas = findViewById(R.id.btnBebidas);

        btnEntradas.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "1");
            startActivity(intent);
        });

        btnPratosQuentes.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "2");
            startActivity(intent);
        });

        btnTemakis.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "3");
            startActivity(intent);
        });

        btnPecas.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "4");
            startActivity(intent);
        });

        btnCombos.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "5");
            startActivity(intent);
        });

        btnBebidas.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "6");
            startActivity(intent);
        });
    }
}
