package com.example.dragonsushi.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.example.dragonsushi.Objects.Bairro;
import com.example.dragonsushi.Objects.Cidade;
import com.example.dragonsushi.Objects.Endereco;
import com.example.dragonsushi.Objects.Estado;
import com.example.dragonsushi.Objects.Logradouro;
import com.example.dragonsushi.R;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

public class AddressActivity extends AppCompatActivity {
    ImageButton imgLocation;
    TextView txtLocation;
    EditText edtxtLogradouro, edtxtNumero, edtxtBairro, edtxtComplemento, edtxtCidade, edtxtUf;
    String logradouro, numero, bairro, complemento, cidade, uf;
    Button btnSalvar;
    String URL = "https://tallpurpleboard19.conveyor.cloud/api/EnderecoApi/";
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_endereco);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        imgLocation = findViewById(R.id.imgLocation);
        txtLocation = findViewById(R.id.txtLocation);
        edtxtLogradouro = findViewById(R.id.edtxtLogradouro);
        edtxtNumero = findViewById(R.id.edtxtNumero);
        edtxtBairro = findViewById(R.id.edtxtBairro);
        edtxtComplemento = findViewById(R.id.edtxtComplemento);
        edtxtCidade = findViewById(R.id.edtxtCidade);
        edtxtUf = findViewById(R.id.edtxtUf);
        btnSalvar = findViewById(R.id.btnSalvar);

        imgLocation.setOnClickListener(v -> {
            getPermission();
        });
        txtLocation.setOnClickListener(v -> {
            getPermission();
        });

        Intent intent = getIntent();
        if(intent.hasExtra("Endereco")){
            Endereco oEndereco = (Endereco) intent.getSerializableExtra("Endereco");

            Logradouro oLogradouro = oEndereco.getLogradouro();
            Bairro oBairro = oEndereco.getBairro();
            Cidade oCidade = oEndereco.getCidade();
            Estado oEstado = oEndereco.getEstado();

            edtxtLogradouro.setText(oLogradouro.getLogradouro());
            edtxtNumero.setText(oEndereco.getNumero());
            edtxtBairro.setText(oBairro.getBairro());
            edtxtComplemento.setText(oEndereco.getComplemento());
            edtxtCidade.setText(oCidade.getCidade());
            edtxtUf.setText(oEstado.getUf());
        } else {
            btnSalvar.setOnClickListener(v -> {
                logradouro = String.valueOf(edtxtLogradouro.getText());
                numero = String.valueOf(edtxtNumero.getText());
                bairro = String.valueOf(edtxtBairro.getText());
                complemento = String.valueOf(edtxtComplemento.getText());
                cidade = String.valueOf(edtxtCidade.getText());
                uf = String.valueOf(edtxtUf.getText());

                if (!uf.equals("SP")){
                    edtxtUf.requestFocus();
                    Toast.makeText(this, "Entregamos apenas em São Paulo", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Logradouro cLogradouro = new Logradouro(logradouro);
                        Bairro cBairro = new Bairro(bairro);
                        Cidade cCidade = new Cidade(cidade);
                        Estado cEstado = new Estado(uf);
                        Endereco cEndereco = new Endereco(cLogradouro, cBairro, cCidade, cEstado, numero, complemento);

                        validarCampos();
                        postDataAddress(cLogradouro, cBairro, cCidade, cEstado);

                        Intent intentAddress = new Intent(this, CarrinhoActivity.class);
                        intentAddress.putExtra("Endereco", cEndereco);
                        Toast.makeText(this, "Endereço salvo", Toast.LENGTH_SHORT).show();
                        startActivity(intentAddress);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    // CADASTRAR ENDEREÇO PELA API
    public void postDataAddress(Logradouro logradouro, Bairro bairro, Cidade cidade, Estado estado){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject oLogradouro = new JSONObject();
            oLogradouro.put("rua", logradouro.getLogradouro());

            JSONObject oBairro = new JSONObject();
            oBairro.put("bairro", bairro.getBairro());

            JSONObject oCidade = new JSONObject();
            oCidade.put("cidade", cidade.getCidade());

            JSONObject oEstado = new JSONObject();
            oEstado.put("idEstado", estado.getUf());

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Endereco", oLogradouro);
            jsonBody.put("Bairro", oBairro);
            jsonBody.put("Cidade", oCidade);
            jsonBody.put("Estado", oEstado);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // REQUISITANDO PERMISSÃO PARA ACESSAR LOCALIZAÇÃO
    private void getPermission(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
    }

    // EXTRAINDO INFORMAÇÕES A PARTIR DA LOCALIZAÇÃO
    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();

            if (location != null) {
                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                    List<Address> addressList = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1
                    );

                    edtxtLogradouro.setText(String.valueOf(addressList.get(0).getThoroughfare()));
                    edtxtNumero.setText(String.valueOf(addressList.get(0).getFeatureName()));
                    edtxtBairro.setText(String.valueOf(addressList.get(0).getSubLocality()));
                    edtxtCidade.setText(String.valueOf(addressList.get(0).getSubAdminArea()));

                    String uf = String.valueOf(addressList.get(0).getAdminArea());
                    if(uf.equals("São Paulo")){
                        edtxtUf.setText("SP");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // VALIDAÇÃO DE CAMPOS
    private void validarCampos() {
        boolean verificacao = false;

        if (verificacao != campoNulo(logradouro)) {
            edtxtLogradouro.requestFocus();
            Toast.makeText(this, "Preencha o campo logradouro", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(numero)) {
            edtxtNumero.requestFocus();
            Toast.makeText(this, "Preencha o campo número", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(bairro)) {
            edtxtBairro.requestFocus();
            Toast.makeText(this, "Preencha o campo bairro", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(cidade)) {
            edtxtCidade.requestFocus();
            Toast.makeText(this, "Preencha o campo cidade", Toast.LENGTH_SHORT).show();
        } else if (verificacao != campoNulo(uf)) {
            edtxtUf.requestFocus();
            Toast.makeText(this, "Preencha o campo UF", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean campoNulo(String campo) {
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }
}
