package com.example.dragonsushi.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dragonsushi.R;

public class CadastroActivity extends AppCompatActivity {

    EditText edtxtNome, edtxtTelefone, edtxtCpf, edtxtEmail, edtxtSenha, edtxtConfSenha;
    Button btnCadastrar;
    String nome, telefone, cpf, email, senha, confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtxtNome = findViewById(R.id.edtxtNome);
        edtxtTelefone = findViewById(R.id.edtxtTelefone);
        edtxtCpf = findViewById(R.id.edtxtCpf);
        edtxtEmail = findViewById(R.id.edtxtEmail);
        edtxtSenha = findViewById(R.id.edtxtSenha);
        edtxtConfSenha = findViewById(R.id.edtxtConfSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(v -> {
            nome = String.valueOf(edtxtNome.getText());
            telefone = String.valueOf(edtxtTelefone.getText());
            cpf = String.valueOf(edtxtCpf.getText());
            email = String.valueOf(edtxtEmail.getText());
            senha = String.valueOf(edtxtSenha.getText());
            confirmar = String.valueOf(edtxtConfSenha.getText());

            validarCampos();

        });
    }

    // VALIDAÇÃO DE CAMPOS

    private void validarCampos() {
        nome = String.valueOf(edtxtNome.getText());
        telefone = String.valueOf(edtxtTelefone.getText());
        cpf = String.valueOf(edtxtCpf.getText());
        email = String.valueOf(edtxtEmail.getText());
        senha = String.valueOf(edtxtSenha.getText());
        confirmar = String.valueOf(edtxtConfSenha.getText());

        if (!campoNulo(nome)) {
            edtxtNome.requestFocus();
            Toast.makeText(this, "Preencha o campo nome.", Toast.LENGTH_SHORT).show();
        } else if (!campoNulo(telefone)) {
            edtxtTelefone.requestFocus();
            Toast.makeText(this, "Preencha o campo telefone.", Toast.LENGTH_SHORT).show();
        } else if (!campoNulo(cpf)) {
            edtxtCpf.requestFocus();
            Toast.makeText(this, "Preencha o campo CPF.", Toast.LENGTH_SHORT).show();
        } else if (!campoNulo(email)) {
            edtxtEmail.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        } else if (!campoNulo(senha)) {
            edtxtSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        } else if (!campoNulo(confirmar)) {
            edtxtConfSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo para confirmar senha.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean campoNulo(String campo) {
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    // SAVED INSTANCE

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        nome = String.valueOf(edtxtNome.getText());
        telefone = String.valueOf(edtxtTelefone.getText());
        cpf = String.valueOf(edtxtCpf.getText());
        email = String.valueOf(edtxtEmail.getText());
        senha = String.valueOf(edtxtSenha.getText());
        confirmar = String.valueOf(edtxtConfSenha.getText());

        outState.putString("nome", nome);
        outState.putString("telefone", telefone);
        outState.putString("cpf", cpf);
        outState.putString("email", email);
        outState.putString("senha", senha);
        outState.putString("confirmar", confirmar);
    }
}
