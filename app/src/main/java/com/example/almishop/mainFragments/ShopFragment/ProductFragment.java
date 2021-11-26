package com.example.almishop.mainFragments.ShopFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.mainFragments.ShopFragment.adapters.ProductDataAdapter;
import com.example.almishop.mainFragments.ShopFragment.adapters.RVGaleriaAdapter;
import com.example.almishop.model.Console;
import com.example.almishop.model.Product;
import com.example.almishop.model.Smartphone;
import com.example.almishop.model.Tablet;
import com.example.almishop.model.Videogame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFragment extends Fragment
{
    private static final String TAG = "productFragment";
    private Context context;
    private String id;
    private ArrayList<String> characteristics = new ArrayList<>();
    private Smartphone smartphone;
    private Tablet tablet;
    private Console console;
    private Videogame videogame;
    private ListView lvCharacteristics = null;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvStock_sale;
    private ImageView ivCover;
    private ImageButton btnBack = null;
    private Button btnAddProduct;
    private MainActivity activity;
    private ArrayList<Product> arraylist = new ArrayList<>();
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;


    public ProductFragment()
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

        id = getArguments().getString("id");
        String id_product_type = getArguments().getString("id_product_type");
       // callProductById(id);
        getProducBytypeAndId(id, id_product_type);


        return inflater.inflate(R.layout.fragment_product, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();

        lvCharacteristics = view.findViewById(R.id.lvproducData);

        ivCover = view.findViewById(R.id.ivProduct);

        tvPrice = view.findViewById(R.id.tvPrice);

        tvStock_sale = view.findViewById(R.id.tvStock_sale);

        btnAddProduct = view.findViewById(R.id.btnAddproduct);

        tvName = view.findViewById(R.id.tvName);

        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity activity = (MainActivity) getActivity();
                activity.navigateTo(activity.mainFragments.get(0));
            }
        });

       /* btnAddProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Se ha añadido el producto al cesta", Toast.LENGTH_SHORT).show();

                if (smartphone != null)
                {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("smartphone", arraylist);
                    activity.shopFragment.setArguments(bundle);
                    activity.navigateTo(activity.shopFragment);
                }

            }
        });*/




    }

    private  void callProductById(String id)
    {

        Call<Smartphone> call = ApiAdapter.getApiService().getSmartphoneById(id);

        call.enqueue(new Callback<Smartphone>()
        {
            @Override
            public void onResponse(Call<Smartphone> call, Response<Smartphone> response)
            {
                if (response.isSuccessful())
                {

                    smartphone = response.body();
                    String sd = (smartphone.getHas_sd().equals("1")) ? "Si":"No";
                    characteristics.add("RAM: " + smartphone.getRam() + "GB");
                    characteristics.add("Almacenamiento: " + smartphone.getStorage() + "GB");
                    characteristics.add("Pulgadas: " + smartphone.getInches() + "`");
                    characteristics.add("Bateria: " + smartphone.getBattery() + "mAh");
                    characteristics.add("Camara: " + smartphone.getCamera() + "MP");
                    characteristics.add("SD: " + sd );
                    characteristics.add("Color: " + smartphone.getColor());
                    arraylist.add(smartphone);

                    tvPrice.setText(smartphone.getPrice() + "€");
                    tvStock_sale.setText("Quedan "+smartphone.getStock_sale()+" unidades");


                    tvName.setText(smartphone.getName());

                    //characteristics.add("SD" +  + "asdas");

                    Glide.with(context).load(smartphone.getCover()).into(ivCover);

                    ProductDataAdapter adapter = new ProductDataAdapter(getContext(), 0, characteristics);
                    lvCharacteristics.setAdapter(adapter);

                    if (smartphone.getImages() != null)
                    {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                        RecyclerView recyclerView = getView().findViewById(R.id.rvImagesProduct);

                        recyclerView.setLayoutManager(layoutManager);


                        RVGaleriaAdapter adapter1 = new RVGaleriaAdapter(smartphone.getImages(), context );


                        recyclerView.setAdapter(adapter1);

                    }

                    String arrlocal;
                    arrlocal = localStorage.getString("products", null);
                    if (arrlocal == null)
                    {
                        arraylist.add(smartphone);
                        String json = arraylist.toString();
                        localStorageEditor.putString("products", json);
                        Toast.makeText(context, "mi smatphone" + smartphone.toString(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "estoy en el else", Toast.LENGTH_SHORT).show();
                        try
                        {
                            //arraylist = arrlocal.
                            JSONObject productObj = new JSONObject(arrlocal);

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Smartphone> call, Throwable t)
            {

            }
        });

    }
    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    public void getProducBytypeAndId(String id, String id_type)
    {
        switch (id_type)
        {
            case "1":
                characteristics.clear();
                Call<Smartphone> call = ApiAdapter.getApiService().getSmartphoneById(id);

                call.enqueue(new Callback<Smartphone>()
                {
                    @Override
                    public void onResponse(Call<Smartphone> call, Response<Smartphone> response)
                    {
                        if (response.isSuccessful())
                        {

                            smartphone = response.body();
                            String sd = (smartphone.getHas_sd().equals("1")) ? "Si":"No";
                            characteristics.add("RAM: " + smartphone.getRam() + "GB");
                            characteristics.add("Almacenamiento: " + smartphone.getStorage() + "GB");
                            characteristics.add("Pulgadas: " + smartphone.getInches() + "`");
                            characteristics.add("Bateria: " + smartphone.getBattery() + "mAh");
                            characteristics.add("Camara: " + smartphone.getCamera() + "MP");
                            characteristics.add("SD: " + sd );
                            characteristics.add("Color: " + smartphone.getColor());

                            tvPrice.setText(smartphone.getPrice() + "€");
                            tvStock_sale.setText("Quedan "+smartphone.getStock_sale()+" unidades");

                            tvName.setText(smartphone.getName());

                            //characteristics.add("SD" +  + "asdas");

                            Glide.with(context).load(smartphone.getCover()).into(ivCover);

                            ProductDataAdapter adapter = new ProductDataAdapter(getContext(), 0, characteristics);
                            lvCharacteristics.setAdapter(adapter);

                            if (smartphone.getImages() != null)
                            {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                                RecyclerView recyclerView = getView().findViewById(R.id.rvImagesProduct);

                                recyclerView.setLayoutManager(layoutManager);


                                RVGaleriaAdapter adapter1 = new RVGaleriaAdapter(smartphone.getImages(), context );


                                recyclerView.setAdapter(adapter1);

                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<Smartphone> call, Throwable t)
                    {

                    }
                });

                break;
            case "2":
                characteristics.clear();
                Call<Tablet> call2 = ApiAdapter.getApiService().getTabletsById(id);

                call2.enqueue(new Callback<Tablet>()
                {
                    @Override
                    public void onResponse(Call<Tablet> call, Response<Tablet> response)
                    {
                        if (response.isSuccessful())
                        {
                            tablet = response.body();
                            String sd = (tablet.getHas_sd().equals("1")) ? "Si":"No";
                            characteristics.add("RAM: " + tablet.getRam() + "GB");
                            characteristics.add("Almacenamiento: " + tablet.getStorage() + "GB");
                            characteristics.add("Pulgadas: " + tablet.getInches() + "`");
                            characteristics.add("Bateria: " + tablet.getBattery() + "mAh");
                            characteristics.add("Camara: " + tablet.getCamera() + "MP");
                            characteristics.add("SD: " + sd );
                            characteristics.add("Color: " + tablet.getColor());

                            tvPrice.setText(tablet.getPrice() + "€");
                            tvStock_sale.setText("Quedan "+tablet.getStock_sale()+" unidades");

                            tvName.setText(tablet.getName());

                            //characteristics.add("SD" +  + "asdas");

                            Glide.with(context).load(tablet.getCover()).into(ivCover);

                            ProductDataAdapter adapter = new ProductDataAdapter(getContext(), 0, characteristics);
                            lvCharacteristics.setAdapter(adapter);

                            if (tablet.getImages() != null)
                            {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                                RecyclerView recyclerView = getView().findViewById(R.id.rvImagesProduct);

                                recyclerView.setLayoutManager(layoutManager);


                                RVGaleriaAdapter adapter1 = new RVGaleriaAdapter(tablet.getImages(), context );


                                recyclerView.setAdapter(adapter1);

                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<Tablet> call, Throwable t)
                    {

                    }
                });

                break;
            case "3":
                characteristics.clear();
                Call<Videogame> call3 = ApiAdapter.getApiService().getVideogamesById(id);

                call3.enqueue(new Callback<Videogame>()
                {
                    @Override
                    public void onResponse(Call<Videogame> call, Response<Videogame> response)
                    {
                        if (response.isSuccessful())
                        {

                            videogame = response.body();
                            //String sd = (videogame.getHas_cd().equals("1")) ? "Si":"No";
                            characteristics.add("Fecha de salida: " + videogame.getRelease_date());
                            characteristics.add("PEGI: " + videogame.getPegi());
                            characteristics.add("Género: " + videogame.getGenre());
                            characteristics.add("Plataforma: " + videogame.getPlatform());
                            characteristics.add("Developer: " + videogame.getDeveloper());

                            tvPrice.setText(videogame.getPrice() + "€");
                            tvStock_sale.setText("Quedan "+videogame.getStock_sale()+" unidades");

                            tvName.setText(videogame.getName());

                            //characteristics.add("SD" +  + "asdas");

                            Glide.with(context).load(videogame.getCover()).into(ivCover);

                            ProductDataAdapter adapter = new ProductDataAdapter(getContext(), 0, characteristics);
                            lvCharacteristics.setAdapter(adapter);

                            if (videogame.getImages() != null)
                            {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                                RecyclerView recyclerView = getView().findViewById(R.id.rvImagesProduct);

                                recyclerView.setLayoutManager(layoutManager);


                                RVGaleriaAdapter adapter1 = new RVGaleriaAdapter(videogame.getImages(), context );


                                recyclerView.setAdapter(adapter1);

                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<Videogame> call, Throwable t)
                    {

                    }
                });
                break;
            case "4":
                characteristics.clear();
                Call<Console> call4 = ApiAdapter.getApiService().getConsolesById(id);

                call4.enqueue(new Callback<Console>()
                {
                    @Override
                    public void onResponse(Call<Console> call, Response<Console> response)
                    {
                        if (response.isSuccessful())
                        {

                            console = response.body();
                            String cd = (console.getHas_cd().equals("1")) ? "Si":"No";
                            characteristics.add("RAM: " + console.getRam());
                            characteristics.add("Almacenamiento: " + console.getStorage());
                            characteristics.add("CD: " + cd);
                            characteristics.add("GPU: " + console.getGpu());
                            characteristics.add("CPU: " + console.getCpu());

                            tvPrice.setText(console.getPrice() + "€");
                            tvStock_sale.setText("Quedan "+console.getStock_sale()+" unidades");

                            tvName.setText(console.getName());

                            //characteristics.add("SD" +  + "asdas");

                            Glide.with(context).load(console.getCover()).into(ivCover);

                            ProductDataAdapter adapter = new ProductDataAdapter(getContext(), 0, characteristics);
                            lvCharacteristics.setAdapter(adapter);

                            if (console.getImages() != null)
                            {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                                RecyclerView recyclerView = getView().findViewById(R.id.rvImagesProduct);

                                recyclerView.setLayoutManager(layoutManager);


                                RVGaleriaAdapter adapter1 = new RVGaleriaAdapter(console.getImages(), context );


                                recyclerView.setAdapter(adapter1);

                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<Console> call, Throwable t)
                    {

                    }
                });
                break;
        }
    }
}
