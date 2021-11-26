package com.example.almishop.mainFragments.ShopFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.almishop.MainActivity;
import com.example.almishop.R;

public class SearchBarFragment extends Fragment {

    private MainActivity activity;
    public ProfileDialogFragment dialog;
    Context context;
    ImageButton btnProfile;
    private SearchView searchView;

    public SearchBarFragment() {
        super();
    }

    @Override
    public void onAttach(Context context)
    {
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
        return inflater.inflate(R.layout.fragment_shop_search_bar, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchBar);
        activity = (MainActivity) getActivity();

        // mas cosas
        btnProfile = view.findViewById(R.id.btnProfile);

//        Glide.with(context).load("url").into(btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity.getLocation();
                dialog = new ProfileDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "Profile");
            }
        });

        searchView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "La barra de búsqueda no está implementada en la demo.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
