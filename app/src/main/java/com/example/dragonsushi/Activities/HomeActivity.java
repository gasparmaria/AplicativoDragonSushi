package com.example.dragonsushi.Activities;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Adapters.MenuFragment;
import com.example.dragonsushi.Objects.Comanda;
import com.example.dragonsushi.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    ImageButton btnEntradas, btnPratosQuentes, btnTemakis, btnPecas, btnCombos, btnBebidas;
    ImageView search, btncarrinho;
    private static final String FILE_NAME = "comanda.json";
    String URL_GETCOMANDA = "https://littleorangestone64.conveyor.cloud/api/ComandaApi/ComandaDelivery";
    int idComanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        displayFragment();

        btnEntradas = findViewById(R.id.btnEntradas);
        btnPratosQuentes = findViewById(R.id.btnPratosQuentes);
        btnTemakis = findViewById(R.id.btnTemakis);
        btnPecas = findViewById(R.id.btnPecas);
        btnCombos = findViewById(R.id.btnCombos);
        btnBebidas = findViewById(R.id.btnBebidas);
        search = findViewById(R.id.menu_search);
        btncarrinho = findViewById(R.id.menu_cart);

        /*btncarrinho.setOnClickListener(v -> {
            getComanda();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intentComanda = new Intent(getApplicationContext(), CarrinhoActivity.class);
            startActivity(intentComanda);
        });*/

        btnEntradas.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "1");
            startActivity(intent);
        });

        btnPratosQuentes.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "2");
            startActivity(intent);
        });

        btnTemakis.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "3");
            startActivity(intent);
        });

        btnPecas.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "4");
            startActivity(intent);
        });

        btnCombos.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "5");
            startActivity(intent);
        });

        btnBebidas.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriaActivity.class);
            intent.putExtra("Categoria", "6");
            startActivity(intent);
        });
    }

    // MENU FRAGMENT
    public void displayFragment() {
        MenuFragment menuFragment = MenuFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.bottom_menu,menuFragment).addToBackStack(null).commit();
    }

    /*private void getComanda(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GETCOMANDA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            idComanda = jsonObject.getInt("idComanda");

                            Comanda comanda = new Comanda();
                            comanda.setIdComanda(idComanda);

                            Gson gson = new Gson();
                            //  Comanda comandaJson = new Comanda();
                            // comandaJson = gson.fromJson(comanda.toString(), Comanda.class);
                            String json = gson.toJson(comanda);
                            gravarDados(json);

                            Log.e("url", "SucessMesage:");
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                            Log.e("url", "onCatch Response: NÃO FOI");
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

    private void gravarDados(String json) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(json.getBytes());
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
    }*/
}