package com.example.dragonsushi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dragonsushi.Objects.Delivery;
import com.example.dragonsushi.R;

import java.util.ArrayList;
import java.util.List;

public class HistoricoListView extends BaseAdapter {
    private final int layout;
    private final Context context;
    List<Delivery> deliveryList = new ArrayList<Delivery>();

    public HistoricoListView(Context context, int layout, List<Delivery> delivery) {
        this.context = context;
        this.layout = layout;
        this.deliveryList = delivery;
    }

    @Override
    public int getCount() {
        return deliveryList.size();
    }

    @Override
    public Object getItem(int position) {
        return deliveryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtData, txtItens, txtTotal;
        LinearLayout constrait;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoricoListView.ViewHolder holder = new HistoricoListView.ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.constrait = row.findViewById(R.id.linearHistorico);
            holder.txtData = row.findViewById(R.id.txtData);
            holder.txtItens = row.findViewById(R.id.txtItens);
            holder.txtTotal = row.findViewById(R.id.txtTotal);
            row.setTag(holder);
        }
        else{
            holder = (HistoricoListView.ViewHolder) row.getTag();
        }

        return row;
    }
}
