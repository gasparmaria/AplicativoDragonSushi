package com.example.dragonsushi.Activities;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.dragonsushi.Objects.Comanda;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DetalhesActivity extends AppCompatActivity {
    ImageView imgProduct;
    ImageButton btnBack, btnMore, btnLess;
    Button btnAdd;
    TextView txtName, txtDescr, txtPrice, txtQntd, txtSubtotal;
    EditText edtxtObs;
    double price, subtotal;
    Integer counter;
    int idComanda;
    String URL_GETCOMANDA = "https://tallpurplemouse41.conveyor.cloud/api/ComandaApi/ComandaDelivery";
    String URL_POSTCOMANDA = "https://tallpurplemouse41.conveyor.cloud/api/ComandaApi/";
    String URL_POSTPEDIDO = "https://tallpurplemouse41.conveyor.cloud/api/PedidoApi/";

    private static final String FILE_NAME = "comanda.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detalhes);

        imgProduct = findViewById(R.id.imgProduto);
        btnBack = findViewById(R.id.btnVoltar);
        btnMore = findViewById(R.id.btnMais);
        btnLess = findViewById(R.id.btnMenos);
        btnAdd = findViewById(R.id.btnAdd);
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComanda(counter, subtotal);
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intentComanda = new Intent(getApplicationContext(), CarrinhoActivity.class);
                startActivity(intentComanda);
            }
        });
    }

    private void getComanda(int counte, double subtotal){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GETCOMANDA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            idComanda = jsonObject.getInt("idComanda");

                            Comanda comanda = new Comanda();
                            comanda.setSubtotal(subtotal);
                            comanda.setIdComanda(idComanda);

                            Gson gson = new Gson();
                          //  Comanda comandaJson = new Comanda();
                           // comandaJson = gson.fromJson(comanda.toString(), Comanda.class);
                            String json = gson.toJson(comanda);
                            gravarDados(json);

                            if(idComanda == 0) {
                                postComanda();
                            } else{
                                Intent intent = getIntent();
                                intent.hasExtra("Product");
                                Product product = (Product) intent.getSerializableExtra("Product");
                                postPedido(counte, edtxtObs.getText().toString(), product.getId(), jsonObject.getInt("idComanda"));
                            }


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

    public void postComanda() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("numMesa", 0);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POSTCOMANDA, new Response.Listener<String>() {
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
            getComanda(counter, subtotal);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // ARMAZENAR DADOS NO ARQUIVO JASON
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
    }


    private double updateCounter(double price){
        txtQntd.setText(counter.toString());
        subtotal = price * counter;
        txtSubtotal.setText(String.format("Subtotal: R$%.2f", subtotal));

        return subtotal;
    }

    private void postPedido(int counter, String descricao,int id,int numComanda){

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject produto = new JSONObject();
            produto.put("idProd", id);

            JSONObject comanda = new JSONObject();
            comanda.put("idComanda", numComanda);

            JSONObject pedido = new JSONObject();
            pedido.put("descrPedido", descricao);
            pedido.put("qtdProd", counter);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Produto",produto);
            jsonBody.put("Comanda",comanda);
            jsonBody.put("Pedido",pedido);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POSTPEDIDO, new Response.Listener<String>() {
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // MENSAGENS
    private void message(){
        Toast.makeText(this, "Login ou senha não correspondem.", Toast.LENGTH_SHORT).show();
    }

    private void nullMessage(){
        Toast.makeText(this, "Login não cadastrado.", Toast.LENGTH_SHORT).show();
    }
}
