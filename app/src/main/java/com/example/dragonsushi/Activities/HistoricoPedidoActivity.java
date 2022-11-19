package com.example.dragonsushi.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Adapters.ProdutoListView;
import com.example.dragonsushi.Adapters.MenuFragment;
import com.example.dragonsushi.Objects.Client;
import com.example.dragonsushi.Objects.Delivery;
import com.example.dragonsushi.Objects.DeliveryViewModel;
import com.example.dragonsushi.Objects.FormaPagamento;
import com.example.dragonsushi.Objects.Pagamento;
import com.example.dragonsushi.Objects.Pedido;
import com.example.dragonsushi.Objects.Person;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HistoricoPedidoActivity extends AppCompatActivity {
    String PARAMETER = "fkPessoa";
    String URL = "https://lostyellowsled41.conveyor.cloud/api/DeliveryApi/HistoricoPedido";
    private static final String FILE_NAME = "usuarioLogado.json";
    private List<DeliveryViewModel> deliveryList = new ArrayList<DeliveryViewModel>();
    ListView listViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_historico);
        listViewProduct = findViewById(R.id.listviewHistorico);
        displayFragment();
        Gson gson = new Gson();
        String clientJson = lerDados();
        Client client = gson.fromJson(clientJson, Client.class);
        getHistorico(client);
    }

    private void getHistorico(Client client){

        Person person = client.getPerson();
        User user = client.getUser();
        Uri builtUri = Uri.parse(URL).buildUpon().appendQueryParameter(PARAMETER, "1").build();
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
                                JSONObject del = objt.getJSONObject("Delivery");
                                JSONObject pag = objt.getJSONObject("Pagamento");
                                JSONObject fpag = objt.getJSONObject("FormaPg");
                                JSONObject p = objt.getJSONObject("Pedido");

                                Delivery delivery = new Delivery();
                                delivery.setData(del.getString("dataDelivery"));

                                Pagamento pagamento = new Pagamento();
                                pagamento.setTotal(pag.getDouble("total"));

                                FormaPagamento fpagamento = new FormaPagamento();
                                fpagamento.setFormaPag(fpag.getString("formaPag"));

                                Pedido pedido = new Pedido();
                                pedido.setIdPedido(p.getInt("idPedido"));

                                DeliveryViewModel vmDelivery = new DeliveryViewModel();
                                vmDelivery.setDelivery(delivery);
                                vmDelivery.setPedido(pedido);
                                vmDelivery.setPagamento(pagamento);
                                vmDelivery.setFormaPagamento(fpagamento);


                                deliveryList.add(vmDelivery);
                            }
                            //ProdutoListView adapter = new ProdutoListView(getApplicationContext(),R.layout.listview_carrinho, deliveryList);
                            listViewProduct = findViewById(R.id.listviewCategoria);
                            //listViewProduct.setAdapter(adapter);
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
