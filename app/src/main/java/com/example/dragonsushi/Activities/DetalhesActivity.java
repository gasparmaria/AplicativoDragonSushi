package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.R;

import java.util.Objects;

public class DetalhesActivity extends AppCompatActivity {
    ImageView imgProduct;
    ImageButton btnBack, btnMore, btnLess;
    Button btnAdd;
    TextView txtName, txtDescr, txtPrice, txtQntd, txtSubtotal;
    EditText edtxtObs;
    int qtd = 0;
    double price, subtotal;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar());
        setContentView(R.layout.activity_detalhes);

        imgProduct = findViewById(R.id.imgProduto);
        btnBack = findViewById(R.id.btnVoltar);
        btnMore = findViewById(R.id.btnMais);
        btnLess = findViewById(R.id.btnMenos);
        btnAdd = findViewById(R.id.btnAdicionar);
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
        txtPrice.setText(new StringBuilder().append(getString(R.string.rs)).append(String.valueOf(product.getPreco())));

        price = product.getPreco();
        btnMore.setOnClickListener(v ->{
            qtd++;
            txtQntd.setText(qtd);
            subtotal = price * qtd;
            txtSubtotal.setText(new StringBuilder().append(R.string.subtotal_).append(String.valueOf(subtotal)));
        });
        btnLess.setOnClickListener(v ->{
            qtd--;
            txtQntd.setText(qtd);
            subtotal = price * qtd;
            txtSubtotal.setText(new StringBuilder().append(R.string.subtotal_).append(String.valueOf(subtotal)));
        });
    }
}
