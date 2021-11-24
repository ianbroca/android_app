package com.example.almishop.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.mainFragments.ShopFragment.ProductFragment;
import com.example.almishop.mainFragments.ShopFragment.SearchBarFragment;

public class BackHomeFragment extends Fragment
{
    private static final String TAG = "backHomeFragment";
    private Context context;
    private ImageButton btnBack = null;


    public BackHomeFragment()
    {
        super();
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_back_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}
