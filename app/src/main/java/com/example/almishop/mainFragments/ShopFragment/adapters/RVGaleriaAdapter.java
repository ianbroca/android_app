package com.example.almishop.mainFragments.ShopFragment.adapters;

import android.content.Context;
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


public class RVGaleriaAdapter extends RecyclerView.Adapter<RVGaleriaAdapter.RecyclerViewHolder>
{

    private static final String TAG = "RecyclerViewAdapter";

    private String[] images;
    private Context mContext;

    public RVGaleriaAdapter(String[] images, Context context )
    {
        this.images = images;
        this.mContext = context;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageProduct;

        public RecyclerViewHolder (View itemView)
        {
            super(itemView);
            this.imageProduct = itemView.findViewById(R.id.ivProduts);
        }

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_gallery_view, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int pos )
    {

        Log.d(TAG, "onBindViewHolder: llamado.");
        Glide.with(mContext)
                .load(images[pos])
                .into(holder.imageProduct);

    }


    @Override
    public int getItemCount()
    {
        return images.length;
    }


}
