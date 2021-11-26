package com.example.almishop.mainFragments.ShopFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.mainFragments.ShopFragment.adapters.RecyclerViewAdapter;
import com.example.almishop.mainFragments.ShopFragment.adapters.ShoppingCartAdapter;
import com.example.almishop.model.Console;
import com.example.almishop.model.HistoryProduct;
import com.example.almishop.model.Product;
import com.example.almishop.model.Sale;
import com.example.almishop.model.Smartphone;
import com.example.almishop.model.Tablet;
import com.example.almishop.model.User;
import com.example.almishop.model.Videogame;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartFragment extends DialogFragment {

    private MainActivity activity;
    private Context context;

    private ImageView btnCloseCart;
    private Button btnBuyCart;
    private TextView tvTotalPrice;

    private ListView listView;
    public ArrayList<Product> listElements;

    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;

    public ShoppingCartFragment() { super(); }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_cart, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) getActivity();
        localStorage = activity.getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        listElements = new ArrayList<>();

        btnCloseCart = view.findViewById(R.id.btnCloseCart);
        btnBuyCart = view.findViewById(R.id.btnBuyCart);
        listView = view.findViewById(R.id.CartListView);
        tvTotalPrice = view.findViewById(R.id.tvTotalCartPrice);

        btnCloseCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.navigateTo(activity.mainFragments.get(0));
            }
        });

        String cart = localStorage.getString(getString(R.string.cart), "");
        cart = cart.replaceFirst("/", "");
        if (cart.length() >= 3)
        {
            ArrayList<String> product_ids, product_types;
            product_ids = new ArrayList<>();
            product_types = new ArrayList<>();
            String[] cart_1 = cart.split("/");
            for (int i = 0; i < cart_1.length; i++) {
                String[] cart_2 = cart_1[i].split(",");
                product_ids.add(cart_2[0]);
                product_types.add(cart_2[1]);
            }
            ArrayList<Product> listElements = new ArrayList<>();
            for (int i = 0; i < product_ids.size(); i++) {
                final Product[] product = new Product[1];
                Log.d("TAG", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + product_ids.get(i));
                switch (product_types.get(i))
                {
                    case "1": // Smartphone
                        Call<Smartphone> callSmartphone = ApiAdapter.getApiService().getSmartphoneById(product_ids.get(i));
                        callSmartphone.enqueue(new Callback<Smartphone>() {
                            @Override
                            public void onResponse(Call<Smartphone> call, Response<Smartphone> response) {
                                listElements.add(response.body());
                            }

                            @Override
                            public void onFailure(Call<Smartphone> call, Throwable t) {

                            }
                        });
                        break;
                    case "2": // Tablet
                        Call<Tablet> callTablet = ApiAdapter.getApiService().getTabletsById(product_ids.get(i));
                        callTablet.enqueue(new Callback<Tablet>() {
                            @Override
                            public void onResponse(Call<Tablet> call, Response<Tablet> response) {
                                listElements.add(response.body());
                            }

                            @Override
                            public void onFailure(Call<Tablet> call, Throwable t) {

                            }
                        });
                        break;
                    case "3": // Videogame
                        Call<Videogame> callVideogame = ApiAdapter.getApiService().getVideogamesById(product_ids.get(i));
                        callVideogame.enqueue(new Callback<Videogame>() {
                            @Override
                            public void onResponse(Call<Videogame> call, Response<Videogame> response) {
                                listElements.add(response.body());
                            }

                            @Override
                            public void onFailure(Call<Videogame> call, Throwable t) {

                            }
                        });
                        break;
                    case "4": // Console
                        Call<Console> callConsole = ApiAdapter.getApiService().getConsolesById(product_ids.get(i));
                        callConsole.enqueue(new Callback<Console>() {
                            @Override
                            public void onResponse(Call<Console> call, Response<Console> response) {
                                listElements.add(response.body());
                            }

                            @Override
                            public void onFailure(Call<Console> call, Throwable t) {

                            }
                        });
                        break;
                    default:
                        break;
                }
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    ShoppingCartAdapter adapter = new ShoppingCartAdapter(context, 0, listElements);
                    listView.setAdapter(adapter);

                    float totalPrice = 0;
                    for (int i = 0; i < listElements.size(); i++) {
                        totalPrice += Float.parseFloat(listElements.get(i).getPrice());
                    }
                    final DecimalFormat df = new DecimalFormat("0.00");
                    tvTotalPrice.setText("Importe total: " + df.format(totalPrice) + "€");
                }
            }, 1000);

            btnBuyCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Sale data = new Sale(Integer.parseInt(localStorage.getString(getString(R.string.id_user), "")), product_ids);
                    Call<Integer> call = ApiAdapter.getApiService().sale(data);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            localStorageEditor.putString(getString(R.string.cart), "").commit();
                            activity.navigateTo(activity.mainFragments.get(0));
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
