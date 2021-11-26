package com.example.almishop.mainFragments.ProfileFragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.almishop.R;
import com.example.almishop.model.HistoryProduct;
import com.example.almishop.model.HistoryTransaction;

import java.util.ArrayList;

public class HistoryListDetailsAdapter extends BaseAdapter
{
    private Context context = null;
    ArrayList<HistoryProduct> listElements = new ArrayList<HistoryProduct>();

    public HistoryListDetailsAdapter(Context context, ArrayList<HistoryProduct> listElements)
    {
        this.context = context;
        this.listElements = listElements;
    }

    @Override
    public int getCount()
    {
        return listElements.size();
    }

    @Override
    public Object getItem(int i)
    {
        return listElements.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.adapter_history_list_details, null);

        ImageView productImage = row.findViewById(R.id.ivProductHistory);
        TextView productName = row.findViewById(R.id.tvOrderProductName);
        TextView productPrice = row.findViewById(R.id.tvOrderProductPrice);
        productName.setText(listElements.get(i).getName());
        productPrice.setText("Importe: " + listElements.get(i).getPrice() + "€");
        Glide
                .with(context)
                .load(listElements.get(i).getCover())
                .centerCrop()
                .into(productImage);

        return row;
    }
}
