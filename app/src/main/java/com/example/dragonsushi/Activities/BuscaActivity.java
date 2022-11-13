package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Objects.ListViewAdapter;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuscaActivity extends AppCompatActivity {
    ImageButton btnSearch;
    EditText edtxtSearch;
    String PARAMETER = "nomeProd";
    String url = "https://longsagecard29.conveyor.cloud/api/ProdutoApi/ConsultarCardapio";
    private List<Product> productList = new ArrayList<Product>();
    ListView listViewProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_busca);

        btnSearch = findViewById(R.id.btnSearch);
        edtxtSearch = findViewById(R.id.edtxtSearch);

        listViewProduct = findViewById(R.id.listviewBusca);


        btnSearch.setOnClickListener(v ->{
            getProd();

        });
    }
    private void getProd(){

        productList.clear();
        Uri builtUri = Uri.parse(url).buildUpon().appendQueryParameter(PARAMETER, edtxtSearch.getText().toString()).build();
        String builtUrl = builtUri.toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, builtUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject objt = response.getJSONObject(i);
                                JSONObject prod = objt.getJSONObject("Produto");

                                Product product1 = new Product();

                                product1.setId(prod.getInt("idProd"));
                                product1.setNome(prod.getString("nomeProd"));
                                product1.setDescricao(prod.getString("descrProd"));
                                product1.setPreco(prod.getDouble("preco"));
                                product1.setImagem(prod.getString("imgProd"));

                                productList.add(product1);

                            }
                            ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(),R.layout.listview_produto, productList);
                            listViewProduct = findViewById(R.id.listviewBusca);
                            listViewProduct.setAdapter(adapter);
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
    }
}
