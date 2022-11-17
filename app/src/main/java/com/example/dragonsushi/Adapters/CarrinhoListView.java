package com.example.dragonsushi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dragonsushi.Objects.Carrinho;
import com.example.dragonsushi.Objects.Carrinho;
import com.example.dragonsushi.R;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoListView extends BaseAdapter {
    private final int layout;
    private final Context context;
    List<Carrinho> carrinhoList = new ArrayList<Carrinho>();

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
        ImageButton btnEditar, btnExcluir;
        LinearLayout constrait;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CarrinhoListView.ViewHolder holder = new CarrinhoListView.ViewHolder();

        Carrinho Carrinho = carrinhoList.get(position);

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.constrait = row.findViewById(R.id.linearCarrinho);
            holder.imgProduto = row.findViewById(R.id.imgProduto);
            holder.txtNomeProduto = row.findViewById(R.id.txtNomeProduto);
            holder.txtObsProduto = row.findViewById(R.id.txtObsProduto);
            holder.txtSubtotalProduto = row.findViewById(R.id.txtSubtotalProduto);
            holder.btnEditar = row.findViewById(R.id.btnEditar);
            holder.btnExcluir = row.findViewById(R.id.btnExcluir);
            row.setTag(holder);
        }
        else{
            holder = (CarrinhoListView.ViewHolder) row.getTag();
        }

        holder.txtNomeProduto.setText(Carrinho.getNomeProd());
        holder.txtObsProduto.setText(Carrinho.getObsPed());
        //holder.imgProduto.setImage(Carrinho.getImgProd()));
        holder.txtNomeProduto.setText(Carrinho.getNomeProd());
        holder.txtSubtotalProduto.setText(Carrinho.getSubPed());
        //holder.txtNomeProduto.setText(Carrinho.getNomeProd());
        //holder.txtNomeProduto.setText(Carrinho.getNomeProd());

        return row;
    }
}
