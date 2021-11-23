package com.example.almishop.mainFragments.ShopFragment.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.almishop.R;
import com.example.almishop.model.Product;
import com.example.almishop.model.Smartphone;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{

    private static final String TAG = "RecyclerViewAdapter";

    private Product[] products;
    private Context mContext;

    public RecyclerViewAdapter(Product[] products, Context context)
    {
        this.products = products;
        this.mContext = context;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageProduct;
        TextView nameProduct;

        public RecyclerViewHolder (View itemView)
        {
            super(itemView);
            this.imageProduct = itemView.findViewById(R.id.ivImageProduct);
            this.nameProduct = itemView.findViewById(R.id.tvNameProduct);
        }

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int pos )
    {

        Log.d(TAG, "onBindViewHolder: llamado.");
        Glide.with(mContext)
                .load(products[pos].getCover())
                .into(holder.imageProduct);

        holder.nameProduct.setText(products[pos].getName());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d(TAG, "onClick: clicked on an image: " + products[holder.getAdapterPosition()].getName());
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return products.length;
    }


}
