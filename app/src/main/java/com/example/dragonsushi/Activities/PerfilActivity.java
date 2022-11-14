package com.example.dragonsushi.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dragonsushi.Adapters.MenuFragment;
import com.example.dragonsushi.Objects.Person;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;

public class PerfilActivity extends AppCompatActivity {
    TextView txtNomeUsuario,txtEmailUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_perfil);
        setContentView(R.layout.activity_login);
        displayFragment();
        txtNomeUsuario = findViewById(R.id.txtNomeUsuario);
        txtEmailUsuario = findViewById(R.id.txtEmailUsuario);

        Intent intent2 = getIntent();
        intent2.hasExtra("User");


        User user = (User) intent2.getSerializableExtra("User");
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);



        txtEmailUsuario.setText(user.getLogin());
    }

    public void displayFragment() {
        // Instancia o fragmento
        MenuFragment menuFragment = MenuFragment.newInstance();
        // Obtem o gerenciado do fragmento e inicia a transação
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Adiciona o fragmento.
        fragmentTransaction.add(R.id.bottom_menu,
                menuFragment).addToBackStack(null).commit();
    }
}