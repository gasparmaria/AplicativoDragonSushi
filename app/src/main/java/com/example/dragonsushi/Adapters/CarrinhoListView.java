package com.example.dragonsushi.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.dragonsushi.Activities.DetalhesActivity;
import com.example.dragonsushi.Activities.HomeActivity;
import com.example.dragonsushi.Objects.Carrinho;
import com.example.dragonsushi.Objects.Carrinho;
import com.example.dragonsushi.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoListView extends BaseAdapter {
    private final int layout;
    private final Context context;
    List<Carrinho> carrinhoList = new ArrayList<Carrinho>();
    String URL_EXCLUIR = "https://littleorangestone64.conveyor.cloud/api/PedidoApi/ExcluirPedido";
    String PARAMETER = "id";

    public CarrinhoListView(Context context, int layout, List<Carrinho> carrinho) {
        this.context = context;
        this.layout = layout;
        this.carrinhoList = carrinho;
    }

    @Override
    public int getCount() {
        return carrinhoList.size();
    }

    @Override
    public Object getItem(int position) {
        return carrinhoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imgProduto;
        TextView txtNomeProduto, txtObsProduto, txtSubtotalProduto;
        ImageButton btnExcluir;
        LinearLayout constrait;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CarrinhoListView.ViewHolder holder = new CarrinhoListView.ViewHolder();

        Carrinho carrinho = carrinhoList.get(position);

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.constrait = row.findViewById(R.id.linearCarrinho);
            holder.imgProduto = row.findViewById(R.id.imgProduto);
            Glide.with(context).load(carrinhoList.get(position).getImgProd()).into(holder.imgProduto);
            holder.txtNomeProduto = row.findViewById(R.id.txtNomeProduto);
            holder.txtObsProduto = row.findViewById(R.id.txtObsProduto);
            holder.txtSubtotalProduto = row.findViewById(R.id.txtSubtotalProduto);
            holder.btnExcluir = row.findViewById(R.id.btnExcluir);
            row.setTag(holder);
        }
        else{
            holder = (CarrinhoListView.ViewHolder) row.getTag();
        }

        holder.txtNomeProduto.setText(carrinho.getNomeProd());
        holder.txtObsProduto.setText(carrinho.getObsPed());
        holder.txtNomeProduto.setText(carrinho.getNomeProd());
        holder.txtSubtotalProduto.setText(String.format("R$%.2f",(carrinho.getSubPed())));

        // EXCLUIR PEDIDO
        holder.btnExcluir.setOnClickListener(v -> {
            Uri builtUri = Uri.parse(URL_EXCLUIR).buildUpon().appendQueryParameter(PARAMETER, String.valueOf(carrinho.getIdPedido())).build();
            String builtUrl = builtUri.toString();

            RequestQueue queue = Volley.newRequestQueue(context);
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
            Intent intenthome = new Intent(context, HomeActivity.class);
            context.startActivity(intenthome);
        });

        return row;
    }

    // MENSAGENS
    private void message(){
        Toast.makeText(context, "Pedido excluido", Toast.LENGTH_SHORT).show();
    }
}
