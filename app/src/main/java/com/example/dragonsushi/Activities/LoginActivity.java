package com.example.dragonsushi.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.dragonsushi.DataBase;
import com.example.dragonsushi.Objects.Client;
import com.example.dragonsushi.Objects.Person;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText edtxtLogin, edtxtSenha;
    Button btnLogin;
    TextView txtCadastro;
    String login, senha;
    String PARAMETER = "login";
    String url = "https://lostashpen80.conveyor.cloud/api/UsuarioApi/ConsultarUsuario";
    DataBase dataBase;
    SQLiteDatabase conection;
    private static final String FILE_NAME = "usuarioLogado.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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

                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                try{
                    NetworkInfo ni = cm.getActiveNetworkInfo();
                    if(ni.isConnected()){
                        getUserData();
                    }
                    else{
                        verificarLogin(edtxtLogin.getText().toString(), edtxtSenha.getText().toString());
                    }
                }catch (Exception e){

                }
            }
        });
    }

    // VERIFICAR LOGIN PELA API
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
                            JSONObject person = jsonObject.getJSONObject("Pessoa");

                            login = user.getString("login");
                            senha = user.getString("senha");

                            if ((edtxtLogin.getText().toString()).equals(login) && (edtxtSenha.getText().toString()).equals(senha)) {
                                Gson gson = new Gson();
                                User user1 = gson.fromJson(user.toString(), User.class);
                                Person person1 = gson.fromJson(person.toString(), Person.class);
                                insertUser(user1, person1);

                                Client client = new Client(user1, person1);
                                String json = gson.toJson(client);
                                gravarDados(json);

                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                message();
                            }

                            Log.e("url", "SucessMesage:");
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                            nullMessage();
                            Log.e("url", "onCatch Response: NÃO FOI");
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

    // VERIFICAR LOGIN PELO BANCO
    public Boolean verificarLogin(String email, String senha){
        Cursor cursor = conection.rawQuery("SELECT * FROM tbPerson WHERE email = ? AND password = ?", new String[] {email, senha});

        return cursor.getCount() > 0;
    }

    // INSERIR USUÁRIO NO BANCO
    private long insertUser(User user, Person person){
        dataBase = new DataBase(getApplicationContext());
        conection = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", user.getIdUsuario());
        values.put("name", person.getNome());
        values.put("email", user.getLogin());
        values.put("password", user.getSenha());

        return conection.insert("tbPerson", null, values);
    }

    // ARMAZENAR DADOS NO ARQUIVO JASON
    private void gravarDados(String json) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(json.getBytes());
            Toast.makeText(this, "Usuário logado.", Toast.LENGTH_SHORT).show();
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

    // VERIFICAÇÃO DE CAMPOS
    private boolean campoNulo (String campo){
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    private void validarCampos(){
        boolean verificacao = false;

        String login = edtxtLogin.getText().toString();
        String senha = edtxtSenha.getText().toString();

        if (verificacao != campoNulo(login)) {
            edtxtLogin.requestFocus();
            Toast.makeText(this, "Preencha o campo login.", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(senha)) {
            edtxtSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        }
    }

    // MENSAGENS
    private void message(){
        Toast.makeText(this, "Login ou senha não correspondem.", Toast.LENGTH_SHORT).show();
    }

    private void nullMessage(){
        Toast.makeText(this, "Login não cadastrado.", Toast.LENGTH_SHORT).show();
    }
}