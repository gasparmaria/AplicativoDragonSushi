package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dragonsushi.Objects.FormaPagamento;
import com.example.dragonsushi.R;

public class PagtoActivity extends AppCompatActivity {
    Button btnCartaoCredito, btnCartaoDebito, btnValeRefeicao, btnDinheiro, btnPix;

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

        btnCartaoCredito.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnCartaoCredito.getText().toString());
            intent.putExtra("Pagamento", formaPagamento);
            startActivity(intent);
        });
        btnCartaoDebito.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnCartaoDebito.getText().toString());
            intent.putExtra("Pagamento", formaPagamento);
            startActivity(intent);
        });
        btnValeRefeicao.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnValeRefeicao.getText().toString());
            intent.putExtra("Pagamento", formaPagamento);
            startActivity(intent);
        });
        btnDinheiro.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnDinheiro.getText().toString());
            intent.putExtra("Pagamento", formaPagamento);
            startActivity(intent);
        });
        btnPix.setOnClickListener(v ->{
            formaPagamento.setFormaPag(btnPix.getText().toString());
            intent.putExtra("Pagamento", formaPagamento);
            startActivity(intent);
        });
    }
}
