package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Adapters.MenuFragment;
import com.example.dragonsushi.Objects.Client;
import com.example.dragonsushi.Objects.Person;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {
    EditText edtxtNome, edtxtTelefone, edtxtEmail, edtxtSenha, edtxtConfSenha;
    Button btnSalvar;
    String nome, telefone, email, senha, confirmar;
    String URL = "https://tallpurpleboard19.conveyor.cloud/api/UsuarioApi/EditarUsuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_editar);
        //displayFragment();

        edtxtNome = findViewById(R.id.edtxtNome);
        edtxtTelefone = findViewById(R.id.edtxtTelefone);
        edtxtEmail = findViewById(R.id.edtxtEmail);
        edtxtSenha = findViewById(R.id.edtxtSenha);
        edtxtConfSenha = findViewById(R.id.edtxtConfSenha);
        btnSalvar = findViewById(R.id.btnSalvar);

        Intent intent = getIntent();
        intent.hasExtra("Cliente");

        Client client = (Client) intent.getSerializableExtra("Cliente");
        Person person = client.getPerson();
        User user = client.getUser();

        edtxtNome.setText(person.getNome());
        edtxtTelefone.setText(person.getTelefone());
        edtxtEmail.setText(user.getLogin());

        btnSalvar.setOnClickListener(v -> {
            nome = edtxtNome.getText().toString();
            telefone = edtxtTelefone.getText().toString();
            email = edtxtEmail.getText().toString();
            senha = edtxtSenha.getText().toString();
            confirmar = edtxtConfSenha.getText().toString();

            Person person1 = new Person(person.getId(),nome, telefone);
            User user1 = new User(user.getIdUsuario(),email, senha);

            validarCampos();

            if(Objects.equals(senha, confirmar)){
                putDataUser(person1, user1);
                Intent intent1 = new Intent(this, PerfilActivity.class);
                Toast.makeText(this, "Alterações efetuadas", Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
            else {
                Toast.makeText(EditActivity.this, "As senhas não correspondem.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void putDataUser(Person person, User user){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject pessoa = new JSONObject();
            pessoa.put("nomePessoa", person.getNome());
            pessoa.put("telefone", person.getTelefone());
            pessoa.put("idPessoa", person.getId());

            JSONObject usuario = new JSONObject();
            usuario.put("login", user.getLogin());
            usuario.put("senha", user.getSenha());
            usuario.put("idUsuario", user.getIdUsuario());


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Pessoa", pessoa);
            jsonBody.put("Usuario", usuario);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // VALIDAÇÃO DE CAMPOS
    private void validarCampos() {
        boolean verificacao = false;

        if (verificacao != campoNulo(nome)) {
            edtxtNome.requestFocus();
            Toast.makeText(this, "Preencha o campo nome.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(telefone)) {
            edtxtTelefone.requestFocus();
            Toast.makeText(this, "Preencha o campo telefone.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(email)) {
            edtxtEmail.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(senha)) {
            edtxtSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(confirmar)) {
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
        email = String.valueOf(edtxtEmail.getText());
        senha = String.valueOf(edtxtSenha.getText());
        confirmar = String.valueOf(edtxtConfSenha.getText());

        outState.putString("nome", nome);
        outState.putString("telefone", telefone);
        outState.putString("email", email);
        outState.putString("senha", senha);
        outState.putString("confirmar", confirmar);
    }

}
