package com.example.almishop.mainFragments.ShopFragment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.almishop.R;

import java.util.List;

public class ProductDataAdapter extends ArrayAdapter
{
    private List<String> arrayList = null;
    private Context context = null;

    public ProductDataAdapter(@NonNull Context context, int resource, @NonNull List<String> arrayList)
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

        convertView = inflater.inflate(R.layout.product_data_view, parent, false);

        TextView tvCharacacteristics = convertView.findViewById(R.id.tvCharacteristics);

        tvCharacacteristics.setText(arrayList.get(position));

        return convertView;
    }
}
