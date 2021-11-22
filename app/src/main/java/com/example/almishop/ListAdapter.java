package com.example.almishop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter
{
    private Context context = null;
    ArrayList<String> listElements = new ArrayList<String>();

    public ListAdapter(Context context, ArrayList<String> listElements)
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
        View row = inflater.inflate(R.layout.adapter_list, null);

        TextView listElement = row.findViewById(R.id.listItem);
        listElement.setText(listElements.get(i));

        return row;
    }
}
