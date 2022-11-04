package com.example.dragonsushi.Objects;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dragonsushi.Activities.ProductActivity;
import com.example.dragonsushi.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private final int layout;
    private final Context context;
    List<Product> productList;

    public ListViewAdapter(Context context, int layout, List<Product> productList) {
        this.layout = layout;
        this.context = context;
        this.productList = productList;
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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListViewAdapter.ViewHolder holder = new ListViewAdapter.ViewHolder();

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

        Product product = productList.get(position);

        row.setOnClickListener(v - {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("Produto", product);
            context.startActivity(intent);
        });

        holder.txtProductName.setText(product.getNome());
        holder.txtProductDescr.setText(product.getDescricao());

        return row;
    }
}
