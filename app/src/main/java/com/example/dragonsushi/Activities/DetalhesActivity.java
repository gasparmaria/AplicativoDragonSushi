package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.R;

import java.util.Objects;

public class DetalhesActivity extends AppCompatActivity {
    ImageView imgProduct;
    ImageButton btnBack, btnMore, btnLess;
    Button btnAdd;
    TextView txtName, txtDescr, txtPrice, txtQntd, txtSubtotal;
    EditText edtxtObs;
    double price, subtotal;
    Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detalhes);

        imgProduct = findViewById(R.id.imgProduto);
        btnBack = findViewById(R.id.btnVoltar);
        btnMore = findViewById(R.id.btnMais);
        btnLess = findViewById(R.id.btnMenos);
        //btnAdd = findViewById(R.id.btnAdicionar);
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
        Glide.with(this).load(product.getImagem()).into(imgProduct);

        price = product.getPreco();

        counter = 0;
        btnMore.setOnClickListener(v ->{
            counter++;
            price = updateCounter(price);
        });
        btnLess.setOnClickListener(v ->{
            counter--;
            price = updateCounter(price);
        });



    }

    private double updateCounter(double price){
        txtQntd.setText(counter.toString());
        subtotal = price * counter;
        txtSubtotal.setText(String.format("Subtotal: R$%.2f", subtotal));

        return subtotal;
    }
}
