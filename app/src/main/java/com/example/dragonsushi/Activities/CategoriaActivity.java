package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Objects.ListViewAdapter;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaActivity extends AppCompatActivity {

    List<Product> productList;
    ListView listViewProduct;
    String PARAMETER = "fkCategoria";
    String url = "https://rightsparklyhen73.conveyor.cloud/api/ProdutoApi/ConsultarCategoria";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        if(intent.hasExtra("Categoria")){

            getProductData();
        }
    }

    private void getProductData(){
        Intent intent = getIntent();
        String category = intent.getStringExtra("Categoria");

        RequestQueue queue = Volley.newRequestQueue(this);
        Uri builtUri = Uri.parse(url).buildUpon().appendQueryParameter(PARAMETER, category).build();
        String builtUrl = builtUri.toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, builtUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONObject object = jsonObject.getJSONObject("Produto");
                                Product product = new Product();

                                product.setId(object.getInt("idProd"));
                                product.setNome(object.getString("nomeProd"));
                                product.setDescricao(object.getString("descrProd"));
                                product.setPreco(object.getDouble("imgProd"));

                                productList.add(product);
                            }

                            listViewProduct = findViewById(R.id.listviewCategoria);
                            ListViewAdapter adapter = new ListViewAdapter(this, R.layout.listview_produto, productList);
                            listViewProduct.setAdapter(adapter);


                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                            nullMessage();
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