package com.example.dragonsushi.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dragonsushi.Adapters.MenuFragment;
import com.example.dragonsushi.Objects.Client;
import com.example.dragonsushi.Objects.Person;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PerfilActivity extends AppCompatActivity {
    TextView txtNome, txtEmail;
    LinearLayout linearEdit, linearHistoric, linearLogout;
    private static final String FILE_NAME = "usuarioLogado.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_perfil);
        displayFragment();

        txtNome = findViewById(R.id.txtNomeUsuario);
        txtEmail = findViewById(R.id.txtEmailUsuario);
        linearEdit = findViewById(R.id.layoutEditar);
        linearHistoric = findViewById(R.id.layoutHistorico);
        linearLogout = findViewById(R.id.layoutLogout);

        Gson gson = new Gson();
        String clientJson = lerDados();
        Client client = gson.fromJson(clientJson, Client.class);

        Person person = client.getPerson();
        User user = client.getUser();

        txtNome.setText(person.getNome());
        txtEmail.setText(user.getLogin());

        linearEdit.setOnClickListener(v ->{
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("Cliente", client);
            startActivity(intent);
        });
        linearHistoric.setOnClickListener(v -> {

        });
        linearLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    // LER DADOS DO ARQUIVO JSON
    private String lerDados() {
        FileInputStream fis;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // MENU FRAGMENT
    public void displayFragment() {
        MenuFragment menuFragment = MenuFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.bottom_menu,menuFragment).addToBackStack(null).commit();
    }
}