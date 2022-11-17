package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Adapters.CarrinhoListView;
import com.example.dragonsushi.Adapters.MenuFragment;
import com.example.dragonsushi.Adapters.ProdutoListView;
import com.example.dragonsushi.Objects.Carrinho;
import com.example.dragonsushi.Objects.Endereco;
import com.example.dragonsushi.Objects.FormaPagamento;
import com.example.dragonsushi.Objects.Logradouro;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {
    TextView txtLimpar, txtAddItem, txtSubtotal, txtTaxa, txtTotal, txtFormaPagto, txtEntrega;
    Button btnPedido;
    String URL_PEDIDO = "https://firstbluebook23.conveyor.cloud/api/PedidoApi/ConsultarPedidos";
    String PARAMETER = "comanda";
    String comanda;
    List<Carrinho> carrinhoList = new ArrayList<Carrinho>();
    ListView listViewProduct;
    String subtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_carrinho);
        displayFragment();

        txtLimpar = findViewById(R.id.txtLimpar);
        txtAddItem = findViewById(R.id.txtAddProduto);
        txtSubtotal = findViewById(R.id.txtSubtotal);
        txtTaxa = findViewById(R.id.txtTaxa);
        txtTotal = findViewById(R.id.txtTotal);
        txtFormaPagto = findViewById(R.id.txtFormaPagto);
        txtEntrega = findViewById(R.id.txtEntrega);
        btnPedido = findViewById(R.id.btnPedido);

        txtAddItem.setOnClickListener(v -> {
            Intent intentItem = new Intent(this, HomeActivity.class);
            startActivity(intentItem);
        });

        txtTaxa.setText("RS5,99");

        Intent intent = getIntent();

        if(intent.hasExtra("Endereco")){
            Endereco address = (Endereco) intent.getSerializableExtra("Endereco");
            Logradouro logradouro = address.getLogradouro();
            txtEntrega.setText(logradouro.getLogradouro());
            txtEntrega.setOnClickListener(v ->{
                Intent intentAddress = new Intent(getApplicationContext(), AddressActivity.class);
                intentAddress.putExtra("Endereco", address);
                startActivity(intentAddress);
            });
        } else{
            txtEntrega.setOnClickListener(v ->{
                Intent intentAddress = new Intent(getApplicationContext(), AddressActivity.class);
                startActivity(intentAddress);
            });
        }

        if(intent.hasExtra("Pagamento")){
            FormaPagamento formaPagamento = (FormaPagamento) intent.getSerializableExtra("Pagamento");
            txtFormaPagto.setText(formaPagamento.getFormaPag());
            txtFormaPagto.setOnClickListener(v ->{
                Intent intentPagto = new Intent(getApplicationContext(), PagtoActivity.class);
                startActivity(intentPagto);
            });
        } else{
            txtFormaPagto.setOnClickListener(v ->{
                Intent intentPagto = new Intent(getApplicationContext(), PagtoActivity.class);
                startActivity(intentPagto);
            });
        }
        if(intent.hasExtra("Comanda")){
            comanda = intent.getStringExtra("Comanda");
            subtotal = intent.getStringExtra("Subtotal");
            getPedido(comanda, subtotal);
        }


    }

    public void getPedido(String comanda, String subtotal){

        Uri builtUri = Uri.parse(URL_PEDIDO).buildUpon().appendQueryParameter(PARAMETER, comanda).build();
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
                                JSONObject pedido = objt.getJSONObject("Pedido");

                                Carrinho carrinho = new Carrinho();
                                carrinho.setNomeProd(prod.getString("nomeProd"));
                                carrinho.setImgProd(prod.getString("preco"));
                                carrinho.setObsPed(pedido.getString("descrPedido"));
                                carrinho.setSubPed(subtotal);


                                carrinhoList.add(carrinho);
                            }
                            CarrinhoListView adapter = new CarrinhoListView(getApplicationContext(),R.layout.listview_carrinho, carrinhoList);
                            listViewProduct = findViewById(R.id.listviewCarrinho);
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

    // MENU FRAGMENT
    public void displayFragment() {
        MenuFragment menuFragment = MenuFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.bottom_menu,menuFragment).addToBackStack(null).commit();
    }
}