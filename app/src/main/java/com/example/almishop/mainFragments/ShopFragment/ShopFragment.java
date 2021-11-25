package com.example.almishop.mainFragments.ShopFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.mainFragments.ShopFragment.adapters.RecyclerViewAdapter;
import com.example.almishop.model.Console;
import com.example.almishop.model.Product;
import com.example.almishop.model.Smartphone;
import com.example.almishop.model.Tablet;
import com.example.almishop.model.User;
import com.example.almishop.model.Videogame;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment
{

    private static final String TAG = "shopFragment";

    private TabLayout tabs = null;
    private Context context;



    public ShopFragment()
    {
        super();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_shop, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "initRecyclerView: init recyclerview");


        tabs = view.findViewById(R.id.tlProducts);


        tabs.addTab(tabs.newTab().setText("Videojuegos"));
        tabs.addTab(tabs.newTab().setText("Smartphones"));
        tabs.addTab(tabs.newTab().setText("Tablets"));
        tabs.addTab(tabs.newTab().setText("Consolas"));

        if (tabs.getSelectedTabPosition() == 0)
        {
            Call<Product[]> call1 = ApiAdapter.getApiService().getVideogames();

            call1.enqueue(new Callback<Product[]>()
            {
                @Override
                public void onResponse(Call<Product[]> call, Response<Product[]> response)
                {
                    if (response.isSuccessful())
                    {

                        Product[] videogames = new Videogame[response.body().length];

                        videogames = response.body();

                        addtoRecycler(videogames);

                    }
                }

                @Override
                public void onFailure(Call<Product[]> call, Throwable t)
                {
                    Log.e(TAG, "onFailure: call de Videogames", t);
                    Toast.makeText(context, "Hubo un error al cargar los datos reinicie la aplicaion", Toast.LENGTH_SHORT).show();
                }
            });

        }
        productsOntabSelected();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    private void productsOntabSelected()
    {

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                Log.d(TAG, "onTabSelected: " + tabs.getSelectedTabPosition());
                switch (tab.parent.getSelectedTabPosition())
                {

                    case 0:


                        Call<Product[]> call1 = ApiAdapter.getApiService().getVideogames();

                        call1.enqueue(new Callback<Product[]>()
                        {
                            @Override
                            public void onResponse(Call<Product[]> call, Response<Product[]> response)
                            {
                                if (response.isSuccessful())
                                {

                                    Product[] videogames = new Videogame[response.body().length];

                                    videogames = response.body();

                                    addtoRecycler(videogames);


                                }
                            }

                            @Override
                            public void onFailure(Call<Product[]> call, Throwable t)
                            {
                                Log.e(TAG, "onFailure: call de Videogames", t);
                                Toast.makeText(context, "Hubo un error al cargar los datos reinicie la aplicaion", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 1:

                        Call<Product[]> call2 = ApiAdapter.getApiService().getSmartphones();

                        call2.enqueue(new Callback<Product[]>()
                        {
                            @Override
                            public void onResponse(Call<Product[]> call, Response<Product[]> response)
                            {
                                if (response.isSuccessful())
                                {

                                    Product[] smartphones = new Smartphone[response.body().length];

                                    smartphones = response.body();

                                    addtoRecycler(smartphones);

                                }
                            }

                            @Override
                            public void onFailure(Call<Product[]> call, Throwable t)
                            {
                                Log.e(TAG, "onFailure: call de Smartphones", t);
                                Toast.makeText(context, "Hubo un error al cargar los datos reinicie la aplicaion", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 2:
                        Call<Product[]> call3 = ApiAdapter.getApiService().getTablets();

                        call3.enqueue(new Callback<Product[]>()
                        {
                            @Override
                            public void onResponse(Call<Product[]> call, Response<Product[]> response)
                            {
                                if (response.isSuccessful())
                                {

                                    Product[] tablets = new Tablet[response.body().length];

                                    tablets = response.body();

                                    addtoRecycler(tablets);

                                }
                            }

                            @Override
                            public void onFailure(Call<Product[]> call, Throwable t)
                            {
                                Log.e(TAG, "onFailure: call de Tablets", t);
                                Toast.makeText(context, "Hubo un error al cargar los datos reinicie la aplicaion", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 3:
                        Call<Product[]> call4 = ApiAdapter.getApiService().getConsoles();

                        call4.enqueue(new Callback<Product[]>()
                        {
                            @Override
                            public void onResponse(Call<Product[]> call, Response<Product[]> response)
                            {
                                if (response.isSuccessful())
                                {


                                    Product[] consoles = new Console[response.body().length];

                                    consoles = response.body();

                                    addtoRecycler(consoles);

                                }
                            }

                            @Override
                            public void onFailure(Call<Product[]> call, Throwable t)
                            {
                                Log.e(TAG, "onFailure: call de Consoles", t);
                                Toast.makeText(context, "Hubo un error al cargar los datos reinicie la aplicaion", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

    }

    private void addtoRecycler(Product[] data)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = getView().findViewById(R.id.rvProducts);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, context, (MainActivity) getActivity() );


        recyclerView.setAdapter(adapter);

    }


}