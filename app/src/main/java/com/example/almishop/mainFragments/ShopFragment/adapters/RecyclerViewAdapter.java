package com.example.almishop.mainFragments.ShopFragment.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.model.Product;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{

    private static final String TAG = "RecyclerViewAdapter";

    private Product[] products;
    private Context mContext;
    private int pos;
    private MainActivity activity1;

    public RecyclerViewAdapter(Product[] products, Context context, MainActivity activity1)
    {
        this.activity1 = activity1;
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

        Animation animFadeOut = AnimationUtils.loadAnimation(mContext,R.anim.fade_out);
        view.startAnimation(animFadeOut);

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

               // AppCompatActivity activity = (AppCompatActivity) view.getContext();

                //Fragment productFragment = new ProductFragment();

               /* productFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, productFragment).addToBackStack(null).commit();*/

               /* Fragment backHome = new BackHomeFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSearchBarView, backHome).addToBackStack(null).commit();*/

                //activity1.navigateTo(activity1.productFragment);

                activity1.navigateto_bundle(bundle, activity1.productFragment);

            }
        });
    }


    @Override
    public int getItemCount()
    {
        return products.length;
    }




}
