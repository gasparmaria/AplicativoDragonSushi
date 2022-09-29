package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dragonsushi.R;

public class LoginActivity extends AppCompatActivity {

    private static final String FILE_NAME = "usuarioLogado.json";
    EditText edtxtLogin, edtxtSenha;
    Button btnLogin;
    TextView txtCadastro;
    String login, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtxtLogin = findViewById(R.id.edtxtLogin);
        edtxtSenha = findViewById(R.id.edtxtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        txtCadastro = findViewById(R.id.txtCadastro);

        btnLogin.setOnClickListener(v -> {
            login = String.valueOf(edtxtLogin.getText());
            senha = String.valueOf(edtxtSenha.getText());

            validarCampos();


        });
    }

    private void validarCampos() {
        login = String.valueOf(edtxtLogin.getText());
        senha = String.valueOf(edtxtSenha.getText());

        if (!campoNulo(login)) {
            edtxtLogin.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        } else if (!campoNulo(senha)) {
            edtxtSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean campoNulo(String campo) {
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        login = String.valueOf(edtxtLogin.getText());
        senha = String.valueOf(edtxtSenha.getText());

        outState.putString("login", login);
        outState.putString("senha", senha);
    }
}
