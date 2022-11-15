package com.example.dragonsushi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dragonsushi.Adapters.MenuFragment;
import com.example.dragonsushi.Objects.Endereco;
import com.example.dragonsushi.Objects.FormaPagamento;
import com.example.dragonsushi.R;

public class CarrinhoActivity extends AppCompatActivity {
    TextView txtLimpar, txtAddItem, txtSubtotal, txtTaxa, txtTotal, txtFormaPagto, txtEntrega;
    Button btnPedido;

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
            txtEntrega.setText(address.getRua());
        } else{
            txtEntrega.setOnClickListener(v ->{
                Intent intentAddress = new Intent(getApplicationContext(), AddressActivity.class);
                startActivity(intentAddress);
            });
        }

        if(intent.hasExtra("Pagamento")){
            FormaPagamento formaPagamento = (FormaPagamento) intent.getSerializableExtra("Pagamento");
            txtFormaPagto.setText(formaPagamento.getFormaPag());
        } else{
            txtFormaPagto.setOnClickListener(v ->{
                Intent intentPagto = new Intent(getApplicationContext(), PagtoActivity.class);
                startActivity(intentPagto);
            });
        }
    }

    // MENU FRAGMENT
    public void displayFragment() {
        MenuFragment menuFragment = MenuFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.bottom_menu,menuFragment).addToBackStack(null).commit();
    }
}