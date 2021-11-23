package com.example.almishop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.almishop.model.StoreImages;
import com.example.almishop.model.Us;

import java.util.ArrayList;

public class GalleryGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<StoreImages> imgList = new ArrayList<>();

    public GalleryGridAdapter(Context context, ArrayList<StoreImages> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int i) {
        return imgList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View imgView = inflater.inflate(R.layout.grid_gallery_image, viewGroup, false);
        ImageView img = imgView.findViewById(R.id.ivGallery);
        TextView txt = imgView.findViewById(R.id.tvGallery);
        txt.setText(imgList.get(i).getBottomText());
        Glide.with(context).load(imgList.get(i).getImgUrl()).centerCrop().into(img);
        return imgView;
    }
}
