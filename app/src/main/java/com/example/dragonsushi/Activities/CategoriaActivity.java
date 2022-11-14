package com.example.dragonsushi.Activities;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Adapters.ListViewAdapter;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<Product>();
    ListView listViewProduct;
    TextView categoria;
    ImageView imgProduto;
    String PARAMETER = "fkCategoria";
    String url = "https://lastshinyapple50.conveyor.cloud/api/ProdutoApi/ConsultarCategoria";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_categoria);
        imgProduto = findViewById(R.id.imgProduto);
        categoria = findViewById(R.id.txtCategoria);

        getCategoria();
    }

    private void getCategoria(){
        Intent intent = getIntent();
        String category = intent.getStringExtra("Categoria");

        switch (category){
            case "1":
                categoria.setText(R.string.entradas);
                break;
            case "2":
                categoria.setText(R.string.pratos_quentes);
                break;
            case "3":
                categoria.setText(R.string.temakis);
                break;
            case "4":
                categoria.setText(R.string.pecas);
                break;
            case "5":
                categoria.setText(R.string.combos);
                break;
            case "6":
                categoria.setText(R.string.bebidas);
                break;
        }

        Uri builtUri = Uri.parse(url).buildUpon().appendQueryParameter(PARAMETER, category).build();
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
                            listViewProduct = findViewById(R.id.listviewCategoria);
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