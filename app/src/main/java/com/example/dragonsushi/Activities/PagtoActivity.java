package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dragonsushi.Objects.FormaPagamento;
import com.example.dragonsushi.R;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PagtoActivity extends AppCompatActivity {
    Button btnCartaoCredito, btnCartaoDebito, btnValeRefeicao, btnDinheiro, btnPix;
    private static final String FILE_NAME = "pagamento.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.acticity_formas_pagamento);

        btnCartaoCredito = findViewById(R.id.btnCartaoCredito);
        btnCartaoDebito = findViewById(R.id.btnCartaoDebito);
        btnValeRefeicao = findViewById(R.id.btnValeRefeicao);
        btnDinheiro = findViewById(R.id.btnDinheiro);
        btnPix = findViewById(R.id.btnPix);

        Intent intent = new Intent(this, CarrinhoActivity.class);
        FormaPagamento formaPagamento = new FormaPagamento();
        Gson gson = new Gson();

        btnCartaoCredito.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnCartaoCredito.getText().toString());

            String json = gson.toJson(formaPagamento);
            gravarDados(json);

            startActivity(intent);
        });
        btnCartaoDebito.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnCartaoDebito.getText().toString());

            String json = gson.toJson(formaPagamento);
            gravarDados(json);

            startActivity(intent);
        });
        btnValeRefeicao.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnValeRefeicao.getText().toString());

            String json = gson.toJson(formaPagamento);
            gravarDados(json);

            startActivity(intent);
        });
        btnDinheiro.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnDinheiro.getText().toString());

            String json = gson.toJson(formaPagamento);
            gravarDados(json);

            startActivity(intent);
        });
        btnPix.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnPix.getText().toString());

            String json = gson.toJson(formaPagamento);
            gravarDados(json);

            startActivity(intent);
        });
    }

    // ARMAZENAR DADOS NO ARQUIVO JSON
    private void gravarDados(String json) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(json.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
