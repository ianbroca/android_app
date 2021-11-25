package com.example.almishop.mainFragments.ShopFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.mainFragments.ShopFragment.adapters.ProductDataAdapter;
import com.example.almishop.mainFragments.ShopFragment.adapters.RVGaleriaAdapter;
import com.example.almishop.model.Smartphone;

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
    private ListView lvCharacteristics = null;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvStock_sale;
    private ImageView ivCover;


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
        callProductById(id);


        return inflater.inflate(R.layout.fragment_product, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        lvCharacteristics = view.findViewById(R.id.lvproducData);

        ivCover = view.findViewById(R.id.ivProduct);

        tvPrice = view.findViewById(R.id.tvPrice);

        tvStock_sale = view.findViewById(R.id.tvStock_sale);

        tvName = view.findViewById(R.id.tvName);

        String [] images = new String[2];

        images[0] = "https://images5.alphacoders.com/414/thumb-1920-414718.jpg";
        images[1] = "https://www.amateurphotographer.co.uk/wp-content/uploads/2020/09/Quiraing-1115-scaled.jpg";

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = getView().findViewById(R.id.rvImagesProduct);

        recyclerView.setLayoutManager(layoutManager);


        RVGaleriaAdapter adapter = new RVGaleriaAdapter(images, context );


        recyclerView.setAdapter(adapter);


        Log.d(TAG, "smaaaaaaaaaaaaaaaaaaaaaaaaarrrrtphoen" + id);




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
                    String sd = (smartphone.getHas_sd() == "1") ? "Si":"No";
                    characteristics.add("RAM: " + smartphone.getRam() + "GB");
                    characteristics.add("Almacenamiento: " + smartphone.getStorage() + "GB");
                    characteristics.add("Pulgadas: " + smartphone.getInches() + "`");
                    characteristics.add("Bateria: " + smartphone.getBattery() + "mAh");
                    characteristics.add("SD: " + sd );
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

    }
    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}
