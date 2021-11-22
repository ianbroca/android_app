
package com.example.almishop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;


public class AboutUsGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Us> devList = new ArrayList<>();

    public AboutUsGridAdapter(Context context, ArrayList<Us> devList) {
        this.context = context;
        this.devList = devList;
    }

    @Override
    public int getCount() {
        return devList.size();
    }

    @Override
    public Object getItem(int i) {
        return devList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return devList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View imgView = inflater.inflate(R.layout.about_us_grid, viewGroup, false);
        ImageView img = imgView.findViewById(R.id.ivDev);
        TextView txt = imgView.findViewById(R.id.tvDevName);
        txt.setText(devList.get(i).getName());
        Glide.with(context).load(devList.get(i).getIconUrl()).centerCrop().into(img);
        return imgView;
    }
}
