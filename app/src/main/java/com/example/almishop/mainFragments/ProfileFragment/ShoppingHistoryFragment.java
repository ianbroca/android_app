package com.example.almishop.mainFragments.ProfileFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.ListAdapter;
import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.model.ChangePassword;
import com.example.almishop.model.HistoryTransaction;
import com.example.almishop.model.Login;
import com.example.almishop.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingHistoryFragment extends DialogFragment {

    private static String TAG = "PASSWORD CHANGE DIALOG";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private MainActivity activity;
    private Context context;

    private ImageView btnCloseHistory;

    private ListView listView;
    private ArrayList<HistoryTransaction> listElements;

    public ShoppingHistoryFragment() { super(); }

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
        return inflater.inflate(R.layout.fragment_shopping_history, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        activity = (MainActivity) getActivity();

        btnCloseHistory = view.findViewById(R.id.btnCloseHistory);

        btnCloseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.navigateTo(activity.mainFragments.get(0));
            }
        });

        listElements = new ArrayList<HistoryTransaction>();
        String id = localStorage.getString(getString(R.string.id_user), "");
        Call<ArrayList<HistoryTransaction>> call = ApiAdapter.getApiService().getShoppingHistory(id);
        call.enqueue(new Callback<ArrayList<HistoryTransaction>>() {
            @Override
            public void onResponse(Call<ArrayList<HistoryTransaction>> call, Response<ArrayList<HistoryTransaction>> response) {
                listElements = response.body();
                HistoryListAdapter adapter = new HistoryListAdapter(context, listElements);
                listView = view.findViewById(R.id.HitoryListView);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<HistoryTransaction>> call, Throwable t) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
