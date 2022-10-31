package com.example.dragonsushi.Activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.DAO.UserDAO;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity  {

    private static final String TAG = "LoginActivity";
    EditText edtxtLogin, edtxtSenha;
    Button btnLogin;
    TextView txtCadastro;
    String url = "https://greatpurplemouse98.conveyor.cloud/api/UsuarioApi/ConsultarUsuario?login=gerente";
    User allUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            edtxtLogin = findViewById(R.id.edtxtLogin);
            edtxtSenha = findViewById(R.id.edtxtSenha);
            btnLogin = findViewById(R.id.btnLogin);
            txtCadastro = findViewById(R.id.txtCadastro);

           getData();
           allUserList = new User();


    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                                 JSONObject user = jsonObject.getJSONObject("Usuario");
                                User singleUser = new User(
                                        user.getInt("idUsuario"),
                                        user.getInt("fkPessoa"),
                                        user.getString("login"),
                                        user.getString("senha"));

                                edtxtLogin.setText(singleUser.getLogin());
                                edtxtSenha.setText(singleUser.getSenha());

                            Log.e("url","SucessMesage:");
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                            Log.e("url", "onCatch Response: NN FOIIIII");
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("url", "onErrorResponse: " + error.getLocalizedMessage());
                    }
                });
            queue.add(stringRequest);
        }

    }


