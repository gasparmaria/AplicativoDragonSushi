package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dragonsushi.Adapters.CarrinhoListView;
import com.example.dragonsushi.Adapters.MenuFragment;
import com.example.dragonsushi.Objects.Carrinho;
import com.example.dragonsushi.Objects.Client;
import com.example.dragonsushi.Objects.Comanda;
import com.example.dragonsushi.Objects.Endereco;
import com.example.dragonsushi.Objects.FormaPagamento;
import com.example.dragonsushi.Objects.Logradouro;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {
    TextView txtLimpar, txtAddItem, txtSubtotal, txtTaxa, txtTotal, txtFormaPagto, txtEntrega;
    Button btnPedido;
    String URL_PEDIDO = "https://lostyellowsled41.conveyor.cloud/api/PedidoApi/ConsultarPedidos";
    String URL_LIMPAR = "https://lostyellowsled41.conveyor.cloud/api/ComandaApi/LimparCarrinho";
    String URL_DELIVERY = "https://lostyellowsled41.conveyor.cloud/api/DeliveryApi";
    String PARAMETER = "comanda";
    List<Carrinho> carrinhoList = new ArrayList<Carrinho>();
    List<Double> subtotalList = new ArrayList<>();
    ListView listViewProduct;
    private static final String FILE_NAME_COMANDA = "comanda.json";
    private static final String FILE_NAME_USUARIO = "usuarioLogado.json";
    private static final String FILE_NAME_ENDERECO = "endereco.json";
    private static final String FILE_NAME_PAGAMENTO = "pagamento.json";

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

        txtTaxa.setText("R$5,99");

        // BOTÃO ADICIONAR MAIS ITENS
        txtAddItem.setOnClickListener(v -> {
            Intent intentItem = new Intent(this, HomeActivity.class);
            startActivity(intentItem);
        });

        Gson gson = new Gson();
        String comandaString = lerDados(FILE_NAME_COMANDA);
        Comanda comanda = gson.fromJson(comandaString, Comanda.class);
        int idComanda = comanda.getIdComanda();
        getPedido(idComanda);

        // BOTÃO LIMPAR CARRINHO
        txtLimpar.setOnClickListener(v ->{
            Uri builtUri = Uri.parse(URL_LIMPAR).buildUpon().appendQueryParameter(PARAMETER, String.valueOf(idComanda)).build();
            String builtUrl = builtUri.toString();

            RequestQueue queue = Volley.newRequestQueue(this);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.DELETE, builtUrl,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(jsonArrayRequest);

            message();
            Intent intenthome = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intenthome);
        });

        // TEXTVIEW DO ENDEREÇO
        String enderecoString = lerDados(FILE_NAME_ENDERECO);
        Endereco endereco = gson.fromJson(enderecoString, Endereco.class);

        if(enderecoString != null){
            Logradouro logradouro = endereco.getLogradouro();
            txtEntrega.setText(logradouro.getLogradouro());
        }

        txtEntrega.setOnClickListener(v -> {
            if(enderecoString != null){
                Intent intentAddress = new Intent(getApplicationContext(), AddressActivity.class);
                intentAddress.putExtra("Endereco", endereco);
                startActivity(intentAddress);
            }
            else{
                Intent intentAddress = new Intent(getApplicationContext(), AddressActivity.class);
                startActivity(intentAddress);
            }
        });

        // TEXTVIEW DO PAGAMENTO
        String pagamentoString = lerDados(FILE_NAME_PAGAMENTO);
        FormaPagamento formaPagamento = gson.fromJson(pagamentoString, FormaPagamento.class);

        if(pagamentoString != null){
            txtFormaPagto.setText(formaPagamento.getFormaPag());
        }

        txtFormaPagto.setOnClickListener(v -> {
            Intent intentPagto = new Intent(getApplicationContext(), PagtoActivity.class);
            startActivity(intentPagto);
        });

        // BOTÃO PARA FINALIZAR O DELIVERY
        btnPedido.setOnClickListener(v ->{
            String valor = txtTotal.getText().toString();
            String nova = valor.replace("R$", "");
            String a = nova.replace(",", ".");
            double total = Double.parseDouble(a);
            postDataDelivery(idComanda, total);
        });
    }

    // LISTVIEW DOS PEDIDOS
    public void getPedido(int comanda){
        Uri builtUri = Uri.parse(URL_PEDIDO).buildUpon().appendQueryParameter(PARAMETER, String.valueOf(comanda)).build();
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
                                        carrinho.setImgProd(prod.getString("imgProd"));
                                        carrinho.setObsPed(pedido.getString("descrPedido"));
                                        carrinho.setIdPedido(pedido.getInt("idPedido"));
                                        int qtdProd = pedido.getInt("qtdProd");
                                        double preco = prod.getDouble("preco");
                                        double result = (preco * qtdProd);
                                        carrinho.setSubPed(result);

                                        carrinhoList.add(carrinho);
                                        subtotalList.add(result);

                                        double subtotal = 0;
                                        for(int x = 0; x < subtotalList.size(); x++){
                                            subtotal = subtotal + subtotalList.get(x);
                                        }
                                        txtSubtotal.setText(String.format("R$%.2f", subtotal));

                                        double total = subtotal + 5.99;
                                        txtTotal.setText(String.format("R$%.2f", total));
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

    // CADASTRANDO O PEDIDO DELIVERY
    public void postDataDelivery(int idComanda, double total){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            Gson gson = new Gson();

            // PEGANDO DADOS DO ENDEREÇO A PARTIR DO JSON
            String enderecoString = lerDados(FILE_NAME_ENDERECO);
            Endereco endereco = gson.fromJson(enderecoString, Endereco.class);
            String numEndereco = endereco.getNumero();
            String descrEndereco = endereco.getComplemento();

            // PEGANDO DADOS DO PAGAMENTO A PARTIR DO JSON
            String pagamentoString = lerDados(FILE_NAME_PAGAMENTO);
            FormaPagamento formaPagamento = gson.fromJson(pagamentoString, FormaPagamento.class);
            String formaPagto = formaPagamento.getFormaPag();

            // PEGANDO DADOS DO CLIENTE A PARTIR DO JSON
            String usuarioString = lerDados(FILE_NAME_USUARIO);
            Client client = gson.fromJson(usuarioString, Client.class);
            Person person = client.getPerson();
            int idPessoa = person.getId();

            // CONSTRUINDO OS OBJETOS DO BODY
            JSONObject pessoa = new JSONObject();
            pessoa.put("idPessoa", idPessoa);

            JSONObject comanda = new JSONObject();
            comanda.put("idComanda", idComanda);

            JSONObject pagamento = new JSONObject();
            pagamento.put("total", total);

            JSONObject formaPg = new JSONObject();
            formaPg.put("formaPag", formaPagto);

            JSONObject end = new JSONObject();
            end.put("idEndereco", "1");

            JSONObject delivery = new JSONObject();
            delivery.put("numEndereco", numEndereco);
            delivery.put("descrEndereco", descrEndereco);

            // CONSTRUINDO O BODY
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Pessoa", pessoa);
            jsonBody.put("Endereco", end);
            jsonBody.put("Comanda", comanda);
            jsonBody.put("Pagamento", pagamento);
            jsonBody.put("FormaPg", formaPg);
            jsonBody.put("Delivery", delivery);

            final String requestBody = jsonBody.toString();

            // POST FROM BODY
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELIVERY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);

            Intent intentsucesso = new Intent(this, ActivitySucesso.class);
            startActivity(intentsucesso);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // LER DADOS DA COMANDA PELO ARQUIVO JSON
    private String lerDados(String FILE_NAME) {
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

    // MENSAGENS
    private void message(){
        Toast.makeText(this, "Carrinho limpo", Toast.LENGTH_SHORT).show();
    }
}