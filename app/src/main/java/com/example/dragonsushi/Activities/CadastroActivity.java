package com.example.dragonsushi.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    EditText edtxtNome, edtxtTelefone, edtxtCpf, edtxtEmail, edtxtSenha, edtxtConfSenha;
    Button btnCadastrar;
    String nome, telefone, cpf, email, senha, confirmar;
    String url = "https://rightsparklyhen73.conveyor.cloud/api/UsuarioApi/Post";

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
            postDataUser();

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

    private void postDataUser() {
        // url to post our data
        String url = "https://rightsparklyhen73.conveyor.cloud/api/";
        RequestQueue queue = Volley.newRequestQueue(CadastroActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {

                    jsonArray = new JSONArray(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObject jsonObject = new JsonObject();
                JSONArray array;

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject user = jsonArray.getJSONObject(i);
                        String login = user.getString("login");
                        String senha = user.getString("senha");
                        JSONObject person = jsonArray.getJSONObject(i);
                        String nomePessoa = person.getString("nomePessoa");
                        String telefone = person.getString("telefone");
                        String cpf = person.getString("cpf");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nomePessoa", edtxtNome.getText().toString());
                params.put("telefone", edtxtTelefone.getText().toString());
                params.put("cpf", edtxtCpf.getText().toString());
                params.put("login", edtxtEmail.getText().toString());
                params.put("senha", edtxtSenha.getText().toString());
                return params;

            }
        };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

    }

}

