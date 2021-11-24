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
import com.example.almishop.mainFragments.ProfileFragment.ChangePasswordFragment;
import com.example.almishop.mainFragments.ProfileFragment.ChangePictureFragment;
import com.example.almishop.mainFragments.ProfileFragment.ChangeProfileFragment;
import com.example.almishop.mainFragments.ProfileFragment.RegisterFragment;
import com.example.almishop.mainFragments.ShopFragment.SearchBarFragment;
import com.example.almishop.mainFragments.ShopFragment.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public LinearLayout searchBar, content;
    BottomNavigationView menu;
    public ArrayList<Fragment> mainFragments;
    public RegisterFragment registerFragment;
    public ChangeProfileFragment changeProfileFragment;
    public ChangePictureFragment changePictureFragment;
    public ChangePasswordFragment changePasswordFragment;
    int selectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

        registerFragment = new RegisterFragment();
        changeProfileFragment = new ChangeProfileFragment();
        changePictureFragment = new ChangePictureFragment();
        changePasswordFragment = new ChangePasswordFragment();

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
        toggleMenu(true);

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
                    case "Localización":
                        selectedIndex = 1;
                        break;
                    case "Galería":
                        selectedIndex = 2;
                        break;
                    case "Sobre nosotros":
                        selectedIndex = 3;
                        break;
                    default:
                        selectedIndex = 0;
                        break;
                }
                navigateTo(mainFragments.get(selectedIndex));
                return false;
            }
        });
    }

    public void navigateTo(Fragment fragment)
    {
        if (mainFragments.contains(fragment))
        {
            toggleMenu(true);
            if (fragment == mainFragments.get(0))
            {
                toggleSearchBar(true);
            } else
            {
                toggleSearchBar(false);
            }
        } else
        {
            toggleMenu(false);
            toggleSearchBar(false);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }

    private void toggleSearchBar(boolean value)
    {
        LayoutParams params = searchBar.getLayoutParams();
        if (value)
        {
            params.height = params.WRAP_CONTENT;
            searchBar.setLayoutParams(params);
        } else
        {
            params.height = 0;
            searchBar.setLayoutParams(params);
        }
    }

    public void toggleMenu(boolean value)
    {
        LayoutParams params = menu.getLayoutParams();
        Log.d("TAG", "toggleMenu: " + params.height);
        if (value)
        {
            params.height = params.WRAP_CONTENT;
            menu.setLayoutParams(params);
        } else
        {
            params.height = 0;
            menu.setLayoutParams(params);
        }
    }
}