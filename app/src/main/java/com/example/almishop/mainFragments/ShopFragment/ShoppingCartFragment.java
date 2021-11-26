package com.example.almishop.mainFragments.ShopFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.model.HistoryProduct;
import com.example.almishop.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartFragment extends DialogFragment {

    private MainActivity activity;
    private Context context;

    private ImageView btnCloseCart;

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
        listView = view.findViewById(R.id.CartListView);

        btnCloseCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.navigateTo(activity.mainFragments.get(0));
            }
        });

        String cart = localStorage.getString(getString(R.string.cart), "");
        String[] cart_1 = cart.split("/");
        for (int i = 0; i < cart_1.length; i++) {
            Log.d("TAG", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + cart_1[i]);
        }

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ArrayList<HistoryProduct> arraylist = listElements.get(i).getProducts();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("arraylist", arraylist);
//                activity.shoppingHistoryDetailsFragment.setArguments(bundle);
//                activity.navigateTo(activity.shoppingHistoryDetailsFragment);
//            }
//        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
