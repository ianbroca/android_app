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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.almishop.R;
import com.example.almishop.mainFragments.BackHomeFragment;
import com.example.almishop.mainFragments.ShopFragment.ProductFragment;
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
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment productFragment = new ProductFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, productFragment).addToBackStack(null).commit();

                Fragment backHome = new BackHomeFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSearchBarView, backHome).addToBackStack(null).commit();

            }
        });
    }


    @Override
    public int getItemCount()
    {
        return products.length;
    }


}
