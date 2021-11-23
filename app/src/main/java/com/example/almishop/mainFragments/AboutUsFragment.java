package com.example.almishop.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.almishop.AboutUsGridAdapter;
import com.example.almishop.R;
import com.example.almishop.model.Us;

import java.util.ArrayList;

public class AboutUsFragment extends Fragment {

    final String pfp1Url = "https://pbs.twimg.com/profile_images/1278012601356730369/ZHZr50Tr_400x400.jpg";
    final String pfp2Url = "https://clasebcn.com/wp-content/uploads/2020/04/harold-thumb.jpg";
    final String pfp3Url = "https://cdn.iconscout.com/icon/free/png-256/face-1659511-1410033.png";
    final String pfp4Url = "https://pm1.narvii.com/7613/baa15398d934e6ba4364151d5e4aa290811474dbr1-804-800v2_hq.jpg";

    final String urlYt ="https://cdn.icon-icons.com/icons2/195/PNG/256/YouTube_23392.png";
    final String urlTw ="https://icons.iconarchive.com/icons/xenatt/minimalism/256/App-Twitter-icon.png";
    final String urlInsta ="data:image/jpeg;base64,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";

    private ArrayList<Us> devList = new ArrayList<>();
    private GridView gvDevs;
    private TextView tvText;

    public AboutUsFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_us, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        devList.clear();
        fillDevArray();

        gvDevs = getView().findViewById(R.id.gvDevs);
        gvDevs.setAdapter(new AboutUsGridAdapter(getContext(), devList));

        tvText = getView().findViewById(R.id.tvText);
        tvText.setMovementMethod(new ScrollingMovementMethod());

        ImageView imgYt = view.findViewById(R.id.ivYt);
        ImageView imgTw = view.findViewById(R.id.ivTw);
        ImageView imgInsta = view.findViewById(R.id.ivInsta);

        Glide.with(getContext()).load(urlYt).centerCrop().into(imgYt);
        Glide.with(getContext()).load(urlTw).centerCrop().into(imgTw);
        Glide.with(getContext()).load(urlInsta).centerCrop().into(imgInsta);

        imgYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("http://www.youtube.com"));

                startActivity(httpIntent);
            }
        });
        imgTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("http://www.twitter.com"));

                startActivity(httpIntent);
            }
        });
        imgInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("http://www.instagram.com"));

                startActivity(httpIntent);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void fillDevArray() {
        devList.add(new Us(1, "Asier", pfp1Url));
        devList.add(new Us(2, "Christian", pfp2Url));
        devList.add(new Us(3, "Ian", pfp3Url));
        devList.add(new Us(4, "Jaime", pfp4Url));
    }
}