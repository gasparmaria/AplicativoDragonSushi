package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText edtxtLogin, edtxtSenha;
    Button btnLogin;
    TextView txtCadastro;
    String login, senha;
    String PARAMETER = "login";
    String url = "https://rightsparklyhen73.conveyor.cloud/api/UsuarioApi/ConsultarUsuario";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtxtLogin = findViewById(R.id.edtxtLogin);
        edtxtSenha = findViewById(R.id.edtxtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        txtCadastro = findViewById(R.id.txtCadastro);
        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();
                getUserData();

            }
        });
    }

    private void getUserData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri builtUri = Uri.parse(url).buildUpon().appendQueryParameter(PARAMETER, edtxtLogin.getText().toString()).build();
        String builtUrl = builtUri.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, builtUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject user = jsonObject.getJSONObject("Usuario");
                                    login = user.getString("login");
                                    senha = user.getString("senha");
                                    if ((edtxtLogin.getText().toString()).equals(login) && (edtxtSenha.getText().toString()).equals(senha)) {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        message();
                                    }
                                    Log.e("url", "SucessMesage:");


                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                                nullMessage();
                                Log.e("url", "onCatch Response: NN FOIIIII");
                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nullMessage();
                Log.e("url", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }








        private boolean campoNulo (String campo){
            boolean verificacao = (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
            return verificacao;
        }
        private void message(){
            Toast.makeText(this, "O login ou senha não correspondem.", Toast.LENGTH_SHORT).show();
        }
        private void nullMessage(){
            Toast.makeText(this, "Não há nehum login com esse nome no sistema", Toast.LENGTH_SHORT).show();
        }


        private void validarCampos(){
            boolean verificacao = false;

            String login = edtxtLogin.getText().toString();
            String senha = edtxtSenha.getText().toString();

            if (verificacao = campoNulo(login)) {
                edtxtLogin.requestFocus();
                Toast.makeText(this, "Preencha o campo login.", Toast.LENGTH_SHORT).show();
            } else if (verificacao = campoNulo(senha)) {
                edtxtSenha.requestFocus();
                Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
            }
        }

    }


