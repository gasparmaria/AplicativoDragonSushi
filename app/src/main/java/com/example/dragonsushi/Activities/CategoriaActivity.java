package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Objects.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {
    ArrayList<String> stringArrayList;
    List<Product> productList;
    ListView listViewProduct;
    String PARAMETER = "fkCategoria";
    String url = "https://longtanbox49.conveyor.cloud/api/ProdutoApi/ConsultarCategoria";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCategoria();
    }
    
    private void getCategoria(){
        Intent intent = getIntent();
        String category = intent.getStringExtra("Categoria");

        Uri builtUri = Uri.parse(url).buildUpon().appendQueryParameter(PARAMETER, category).build();
        String builtUrl = builtUri.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, builtUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().create();

                        if(response != null){
                            JSONObject json = null;

                            try{
                                json = new JSONObject(response);
                                JSONArray jsonArray = json.getJSONArray("Produto");
                                stringArrayList = new ArrayList<>();
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    stringArrayList.add(String.valueOf(jsonObject));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("url", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });




        /*StringRequest request = new StringRequest(Request.Method.GET, builtUrl, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("Produto");
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }


                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        Product product = new Product();

                        product.setId(object.getInt("idProd"));
                        product.setNome(object.getString("nomeProd"));
                        product.setDescricao(object.getString("descrProd"));
                        product.setPreco(object.getDouble("imgProd"));

                        //productList.add(product);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //listViewProduct = findViewById(R.id.listviewCategoria);
        //ListViewAdapter adapter = new ListViewAdapter(this, R.layout.listview_produto, productList);
        //listViewProduct.setAdapter(adapter);
        queue.add(request);*/
    }
}