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

import com.bumptech.glide.Glide;
import com.example.almishop.R;

public class SearchBarFragment extends Fragment {
    public ProfileDialogFragment dialog;
    Context context;
    ImageButton btnProfile;

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
        // mas cosas
        btnProfile = view.findViewById(R.id.btnProfile);

//        Glide.with(context).load("url").into(btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog = new ProfileDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "Profile");
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
