package com.example.almishop.mainFragments.ProfileFragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.almishop.R;
import com.example.almishop.model.HistoryTransaction;

import java.util.ArrayList;

public class HistoryListAdapter extends BaseAdapter
{
    private Context context = null;
    ArrayList<HistoryTransaction> listElements = new ArrayList<HistoryTransaction>();

    public HistoryListAdapter(Context context, ArrayList<HistoryTransaction> listElements)
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

        TextView orderNumber = row.findViewById(R.id.tvOrderNumber);
        TextView orderDate = row.findViewById(R.id.tvOrderDate);
        orderNumber.setText(listElements.get(i).getId());
        orderDate.setText(listElements.get(i).getDate());

        return row;
    }
}
