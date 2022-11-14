package com.example.dragonsushi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.dragonsushi.Activities.CategoriaActivity;
import com.example.dragonsushi.Activities.DetalhesActivity;
import com.example.dragonsushi.Activities.ProductActivity;
import com.example.dragonsushi.Objects.Product;
import com.example.dragonsushi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ListViewAdapter extends BaseAdapter {
    private final int layout;
    private final Context context;
    List<Product> productList = new ArrayList<Product>();

    public ListViewAdapter(Context context, int layout, List<Product> product1) {
        this.context = context;
        this.layout = layout;
        this.productList = product1;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        View itemlist;
        TextView txtProductName, txtProductDescr, txtProductPrice;
        ImageView imgProduct;
        LinearLayout constrait;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.constrait = row.findViewById(R.id.linearLayout2);
            holder.txtProductName = row.findViewById(R.id.txtNomeProduto);
            holder.txtProductDescr = row.findViewById(R.id.txtDescrProduto);
            holder.txtProductPrice = row.findViewById(R.id.txtPrecoProduto);
            holder.imgProduct = row.findViewById(R.id.imgProduto);
            Glide.with(context).load(productList.get(position).getImagem()).into(holder.imgProduct);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Product product1 = productList.get(position);

        holder.constrait.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetalhesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Product", product1);
            context.startActivity(intent);
        });

        holder.txtProductName.setText(product1.getNome());
        holder.txtProductDescr.setText(product1.getDescricao());
        holder.txtProductPrice.setText(new StringBuilder().append("R$").append(Double.toString(product1.getPreco())).toString());

        return row;
    }
}
