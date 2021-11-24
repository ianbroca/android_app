package com.example.almishop.mainFragments.ShopFragment.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.mainFragments.BackHomeFragment;
import com.example.almishop.mainFragments.ShopFragment.ProductFragment;
import com.example.almishop.model.Product;
import com.example.almishop.model.Smartphone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{

    private static final String TAG = "RecyclerViewAdapter";

    private Product[] products;
    private Context mContext;
    private int pos;

    public RecyclerViewAdapter(Product[] products, Context context )
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_products_view, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") final int pos )
    {
        this.pos = pos;

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
                Log.d(TAG, "ID DE PRODUCTOOOOOOOOOO " + products[pos].getId() + " id product type" + products[pos].getId_product_type());
                Bundle bundle = new Bundle();
                bundle.putString("id", products[pos].getId());
                bundle.putString("id_product_type", products[pos].getId_product_type());

                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                Fragment productFragment = new ProductFragment();

                productFragment.setArguments(bundle);
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
