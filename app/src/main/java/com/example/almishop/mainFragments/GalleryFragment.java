package com.example.almishop.mainFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.almishop.AboutUsGridAdapter;
import com.example.almishop.GalleryGridAdapter;
import com.example.almishop.R;
import com.example.almishop.model.StoreImages;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private ArrayList<StoreImages> galleryList = new ArrayList<>();
    private GridView gvGallery;

    public GalleryFragment() {
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
        return inflater.inflate(R.layout.fragment_gallery, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryList.clear();
        arrayFiller();

        gvGallery = getView().findViewById(R.id.gvGallery);
        gvGallery.setAdapter(new GalleryGridAdapter(getContext(), galleryList));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void arrayFiller() {
        galleryList.add(new StoreImages("https://videogamestoresblog.files.wordpress.com/2016/06/7.jpg", "Interior de la tienda"));
        galleryList.add(new StoreImages("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGALjLAff2CxvfKjYFW47QsdoMvewQ5t6hYw&usqp=CAU", "Interior de la tienda"));
        galleryList.add(new StoreImages("https://i2.wp.com/nintendosoup.com/wp-content/uploads/2017/10/nintendo_switch_retail_japan_photo_1.jpg?resize=1024%2C576&ssl=1", "Interior de la tienda"));
        galleryList.add(new StoreImages("https://media.blogto.com/articles/7a51-201667-videogames-lead.jpg?w=2048&cmd=resize_then_crop&height=1365&quality=70", "Interior de la tienda"));
        galleryList.add(new StoreImages("https://i1.wp.com/thelincolnite.co.uk/wp-content/uploads/2019/05/Game-7.jpg?resize=800%2C450&ssl=1", "Interior de la tienda"));
        galleryList.add(new StoreImages("https://gamesandstuff.com/sites/default/files/styles/photo_gallery_large/public/Card_3.jpeg?itok=MYOZIOxO", "Interior de la tienda"));
        galleryList.add(new StoreImages("https://lh3.googleusercontent.com/proxy/toG9cmFuCqIXR0uiit4ZuC1EnPcB1u7WZh8FSOL0R7YMBtSTjnovsfFzg0X3zh_ZgtNFz0ZftLbceKn6BWfyFff3NxBw_sRzRrxs9_98loh8Ow3nDUMvVjI", "El almacen"));
        galleryList.add(new StoreImages("https://s.wsj.net/public/resources/images/BF-AG277_XBOX_G_20131122215127.jpg", "El almacen"));
        galleryList.add(new StoreImages("https://cdn-s-www.estrepublicain.fr/images/56E7693F-4EF1-47A0-BB6F-62744AF8A049/NW_raw/la-nouvelle-console-de-jeux-video-de-microsoft-la-xbox-one-est-sortie-vendredi-photo-afp-marion-ruszniewski-1446475529.jpg", "El almacen"));
        galleryList.add(new StoreImages("https://lh3.googleusercontent.com/proxy/vX_5nkS15MOGzP48cxDe4S6ZXaHrl9IL4xSllv2uF25ZNjm_StpFzYoTDoGp5n5eqo6YP7Pi4DjHgC9ZjO1SxVhZj-492ddf6EFjUKneXTb6Eonh02rQcgW19QRViR0DPMSBe85pRWSvnVMJitJu645xyrL64CYKtg", "El almacen"));
    }
}