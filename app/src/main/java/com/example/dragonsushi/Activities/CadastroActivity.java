package com.example.dragonsushi.Activities;

import android.net.Uri;
import android.os.Bundle;
import retrofit2.Call;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Objects.Client;
import com.example.dragonsushi.Objects.Person;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;
import com.example.dragonsushi.Service.UserClient;
import com.android.volley.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit.RetrofitError;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CadastroActivity extends AppCompatActivity {

    EditText edtxtNome, edtxtTelefone, edtxtCpf, edtxtEmail, edtxtSenha, edtxtConfSenha;
    Button btnCadastrar;
    String nome, telefone, cpf, email, senha, confirmar;
    String url = "https://lostashpen80.conveyor.cloud/api/UsuarioApi/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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

            Person person = new Person(nome, telefone, cpf);
            User user = new User(email, senha);
            Client client = new Client(user, person);

            validarCampos();
            postDataUser(person, user);

            /*if(Objects.equals(senha, confirmar))
                postDataUser(client);
            else
                Toast.makeText(CadastroActivity.this, "As senhas não correspondem.", Toast.LENGTH_SHORT).show();*/
        });
    }

    /*private void postDataUser(Client client){
        try {
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            UserClient userClient = retrofit.create(UserClient.class);
            Call<Client> call = userClient.createAccount(client);

            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    Toast.makeText(CadastroActivity.this, "certo", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Toast.makeText(CadastroActivity.this, "errado", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Toast.makeText(CadastroActivity.this, "errado2", Toast.LENGTH_SHORT).show();
        }
    }*/

    /*private void postDataUser(Client client) {
        RequestQueue queue = Volley.newRequestQueue(CadastroActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CadastroActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                taskService.createTask(client, new Callback<Client>(){});


                Map<String, String> params = new HashMap<String, String>();

                params.put("nomePessoa", name);
                params.put("telefone", phone);
                params.put("cpf", id);
                params.put("login", e_mail);
                params.put("senha", password);

                return params;
            }
        };
        queue.add(request);
    }*/

    public void postDataUser(Person person, User user){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject pessoa = new JSONObject();
            pessoa.put("nomePessoa", person.getNome());
            pessoa.put("telefone", person.getTelefone());
            pessoa.put("cpf", person.getCpf());

            JSONObject usuario = new JSONObject();
            usuario.put("login", user.getLogin());
            usuario.put("senha", user.getSenha());


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Pessoa", pessoa);
            jsonBody.put("Usuario", usuario);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
        } else if (verificacao != campoNulo(cpf)) {
            edtxtCpf.requestFocus();
            Toast.makeText(this, "Preencha o campo CPF.", Toast.LENGTH_SHORT).show();
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

