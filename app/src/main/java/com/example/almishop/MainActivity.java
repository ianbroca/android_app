package com.example.almishop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

import com.example.almishop.io.ApiAdapter;
import com.example.almishop.mainFragments.AboutUsFragment;
import com.example.almishop.mainFragments.ShopFragment.BackHomeFragment;
import com.example.almishop.mainFragments.GalleryFragment;
import com.example.almishop.mainFragments.LocationFragment;
import com.example.almishop.mainFragments.ProfileFragment.ChangePasswordFragment;
import com.example.almishop.mainFragments.ProfileFragment.ChangePictureFragment;
import com.example.almishop.mainFragments.ProfileFragment.ChangeProfileFragment;
import com.example.almishop.mainFragments.ProfileFragment.RegisterFragment;
import com.example.almishop.mainFragments.ShopFragment.ProductFragment;
import com.example.almishop.mainFragments.ShopFragment.SearchBarFragment;
import com.example.almishop.mainFragments.ShopFragment.ShopFragment;
import com.example.almishop.model.Location;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public LinearLayout searchBar, content;
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private BottomNavigationView menu;
    public ArrayList<Fragment> mainFragments;
    public RegisterFragment registerFragment;
    public ChangeProfileFragment changeProfileFragment;
    public ChangePictureFragment changePictureFragment;
    public ChangePasswordFragment changePasswordFragment;
    public  ProductFragment productFragment;
    public  BackHomeFragment backHomeFragment;
    int selectedIndex = 0;

    private static String TAG = "MAIN ACTIVITY";

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        localStorage = this.getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

        registerFragment = new RegisterFragment();
        changeProfileFragment = new ChangeProfileFragment();
        changePictureFragment = new ChangePictureFragment();
        changePasswordFragment = new ChangePasswordFragment();
        backHomeFragment = new BackHomeFragment();
        productFragment = new ProductFragment();

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
                .replace(R.id.fragmentSearchBarView, new SearchBarFragment())
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
                        Fragment searchbar = new SearchBarFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSearchBarView, searchbar).addToBackStack(null).commit();
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
            params.height = LayoutParams.WRAP_CONTENT;
        } else
        {
            params.height = 0;
        }
        searchBar.setLayoutParams(params);
    }

    public void toggleMenu(boolean value)
    {
        LayoutParams params = menu.getLayoutParams();
        Log.d("TAG", "toggleMenu: " + params.height);
        if (value)
        {
            menu.setVisibility(View.VISIBLE);
        } else
        {
            menu.setVisibility(View.GONE);
        }
    }

    public void getLocation() {
        if (!localStorage.getString(getString(R.string.id_user), "").equals("")) {
            locationTrack = new LocationTrack(MainActivity.this);


            if (locationTrack.canGetLocation()) {


                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();

                Location locationData = new Location(Integer.parseInt(localStorage.getString(getString(R.string.id_user), "")), longitude, latitude);
                Call<Integer> postLocationCall = ApiAdapter.getApiService().sendLocation(locationData);
                postLocationCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body() == 200) {
                            Log.d(TAG, "sendLocation OK!");
                        } else if (response.body() != 200) {
                            Log.e(TAG, "sendLocation KO - response != 200");
                        } else {
                            Log.e(TAG, "sendLocation KO - response NOT successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e(TAG, "sendLocation KO - ERROR: " + t);
                    }
                });
            } else {

                locationTrack.showSettingsAlert();
            }
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void navigateto_bundle(Bundle bundle, Fragment fragment)
    {
        /*Fragment backHome = new BackHomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_back_view, backHome).addToBackStack(null).commit();*/
        fragment.setArguments(bundle);
        navigateTo(fragment);
    }
}