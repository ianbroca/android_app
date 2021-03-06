package com.example.almishop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

import com.example.almishop.mainFragments.AboutUsFragment;
import com.example.almishop.mainFragments.GalleryFragment;
import com.example.almishop.mainFragments.LocationFragment;
import com.example.almishop.mainFragments.ShopFragment.SearchBarFragment;
import com.example.almishop.mainFragments.ShopFragment.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public final int SEARCH_BAR_HEIGHT = 200;
    public LinearLayout searchBar, content;
    BottomNavigationView menu;
    ArrayList<Fragment> mainFragments;
    int selectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.fragmentSearchBarView);
        content = findViewById(R.id.fragmentContainerView);
        menu = findViewById(R.id.bottom_navbar);
        mainFragments = new ArrayList<>();
        mainFragments.add(new ShopFragment());
        mainFragments.add(new LocationFragment());
        mainFragments.add(new GalleryFragment());
        mainFragments.add(new AboutUsFragment());

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentSearchBarView, new SearchBarFragment())
                .commit();
        toggleSearchBar(true);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainerView, mainFragments.get(selectedIndex))
                .commit();


        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.toString())
                {
                    case "Localizaci??n":
                        selectedIndex = 1;
                        toggleSearchBar(false);
                        break;
                    case "Galer??a":
                        selectedIndex = 2;
                        toggleSearchBar(false);
                        break;
                    case "Sobre nosotros":
                        selectedIndex = 3;
                        toggleSearchBar(false);
                        break;
                    default:
                        selectedIndex = 0;
                        toggleSearchBar(true);
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, mainFragments.get(selectedIndex))
                        .commit();
                return false;
            }
        });
    }

    private void toggleSearchBar(boolean value)
    {
        LayoutParams params = searchBar.getLayoutParams();
        if (value)
        {
            params.height = SEARCH_BAR_HEIGHT;
            searchBar.setLayoutParams(params);
        } else
        {
            params.height = 0;
            searchBar.setLayoutParams(params);
        }
    }
}