package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.dragonsushi.Objects.Client;
import com.example.dragonsushi.Objects.Person;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

public class DetalhesActivity extends AppCompatActivity {
    ImageView imgProduct;
    ImageButton btnBack, btnMore, btnLess;
    Button btnAdd;
    TextView txtName, txtDescr, txtPrice, txtQntd, txtSubtotal;
    EditText edtxtObs;
    double price, subtotal;
    Integer counter;
    String URL = "https://tallpurpleboard19.conveyor.cloud/api/ComandaApi/ComandaDelivery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detalhes);

        imgProduct = findViewById(R.id.imgProduto);
        btnBack = findViewById(R.id.btnVoltar);
        btnMore = findViewById(R.id.btnMais);
        btnLess = findViewById(R.id.btnMenos);
        //btnAdd = findViewById(R.id.btnAdicionar);
        txtName = findViewById(R.id.txtNomeProduto);
        txtDescr = findViewById(R.id.txtDescr);
        txtPrice = findViewById(R.id.txtPrecoProduto);
        txtQntd = findViewById(R.id.txtQuantidade);
        txtSubtotal = findViewById(R.id.txtSubtotal);
        edtxtObs = findViewById(R.id.edtxtObservacao);

        btnBack.setOnClickListener(v ->{
            Intent it = new Intent(this, CategoriaActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(it);
        });

        Intent intent = getIntent();
        intent.hasExtra("Product");

        Product product = (Product) intent.getSerializableExtra("Product");

        txtName.setText(product.getNome());
        txtDescr.setText(product.getDescricao());
        txtPrice.setText(String.format("R$%.2f", product.getPreco()));
        Glide.with(this).load(product.getImagem()).into(imgProduct);

        price = product.getPreco();

        counter = 0;
        btnMore.setOnClickListener(v ->{
            counter++;
            subtotal = updateCounter(price);
            if(counter > 0){
                btnLess.setEnabled(true);
            }
        });
        btnLess.setOnClickListener(v ->{
            counter--;
            subtotal = updateCounter(price);
            if (counter < 1) {
                btnLess.setEnabled(false);
            }
        });
    }

    private Boolean getComanda(){
        RequestQueue queue = Volley.newRequestQueue(this);
        final boolean resposta;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            resposta = jsonObject.getBoolean("0");

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
        return resposta;
    }

    public void setComanda(){

    }

    private double updateCounter(double price){
        txtQntd.setText(counter.toString());
        subtotal = price * counter;
        txtSubtotal.setText(String.format("Subtotal: R$%.2f", subtotal));

        return subtotal;
    }


    // MENSAGENS
    private void message(){
        Toast.makeText(this, "Login ou senha não correspondem.", Toast.LENGTH_SHORT).show();
    }

    private void nullMessage(){
        Toast.makeText(this, "Login não cadastrado.", Toast.LENGTH_SHORT).show();
    }
}
