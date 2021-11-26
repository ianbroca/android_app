package com.example.almishop.mainFragments.ProfileFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.model.HistoryProduct;
import com.example.almishop.model.HistoryTransaction;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingHistoryDetailsFragment extends DialogFragment {

    private static String TAG = "PASSWORD CHANGE DIALOG";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private MainActivity activity;
    private Context context;

    private ImageView btnCloseHistory;
    private TextView tvTitle, tvTotalPrice;
    private ListView listView;
    private ArrayList<HistoryProduct> listElements;

    public ShoppingHistoryDetailsFragment() { super(); }

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
        return inflater.inflate(R.layout.fragment_shopping_history_details, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        activity = (MainActivity) getActivity();

        btnCloseHistory = view.findViewById(R.id.btnCloseHistoryDetails);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        listView = view.findViewById(R.id.HistoryDetailsListView);

        btnCloseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.navigateTo(activity.shoppingHistoryFragment);
            }
        });

        listElements = new ArrayList<HistoryProduct>();
        String id = localStorage.getString(getString(R.string.id_user), "");
        Call<ArrayList<HistoryTransaction>> call = ApiAdapter.getApiService().getShoppingHistory(id);
        listElements = (ArrayList<HistoryProduct>) getArguments().getSerializable("arraylist");
        HistoryListDetailsAdapter adapter = new HistoryListDetailsAdapter(context, listElements);
        listView.setAdapter(adapter);

        float totalPrice = 0;
        for (int i = 0; i < listElements.size(); i++) {
            totalPrice += Float.parseFloat(listElements.get(i).getPrice());
        }
        final DecimalFormat df = new DecimalFormat("0.00");
        tvTotalPrice.setText("Importe total: " + df.format(totalPrice) + "€");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
