package com.example.dragonsushi.Objects;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.dragonsushi.Activities.CategoriaActivity;
import com.example.dragonsushi.Activities.ProductActivity;
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
        TextView txtProdutcId, txtProductName, txtProductDescr, txtProductPrice;
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

            holder.txtProductName = row.findViewById(R.id.txtNomeProduto);
            holder.txtProductDescr = row.findViewById(R.id.txtDescrProduto);
            holder.txtProductPrice = row.findViewById(R.id.txtPrecoProduto);
            holder.imgProduct = row.findViewById(R.id.imgProduto);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Product product1 = productList.get(position);

        row.setOnClickListener(v ->{
            Intent intent = new Intent(context, CategoriaActivity.class);
            intent.putExtra("Produto", (Serializable) product1);
            context.startActivity(intent);
        });

        holder.txtProductName.setText(product1.getNome());
        holder.txtProductDescr.setText(product1.getDescricao());
        holder.txtProductPrice.setText(Double.toString(product1.getPreco()));

        return row;
    }
}
