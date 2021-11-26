package com.example.almishop.mainFragments.ShopFragment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.almishop.R;
import com.example.almishop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends ArrayAdapter
{
    private ArrayList<Product> arrayList = null;
    private Context context = null;

    public ShoppingCartAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> arrayList)
    {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount()
    {
        return arrayList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position)
    {
        return arrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        Log.d(null, "aqui esta mi vistaaaaaaaa"+ arrayList.get(position));
        LayoutInflater inflater = LayoutInflater.from(context);

        convertView = inflater.inflate(R.layout.adapter_shopping_cart_adapter, parent, false);

        ImageView ivImage = convertView.findViewById(R.id.ivCartProductImage);
        TextView tvName = convertView.findViewById(R.id.tvCartProductName);
        TextView tvPrice = convertView.findViewById(R.id.tvCartProductPrice);

        tvName.setText(arrayList.get(position).getName());
        tvPrice.setText(arrayList.get(position).getPrice() + "€");
        Glide.with(context)
                .load(arrayList.get(position).getCover())
                .centerCrop()
                .into(ivImage);

        return convertView;
    }
}
