package com.example.almishop.mainFragments.ProfileFragment;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.model.ChangePicture;
import com.example.almishop.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePictureFragment extends Fragment {
    private static String TAG = "PICTURE CHANGE DIALOG";
    private static final int REQUEST_CODE_TAKE_PHOTO = 0;
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private MainActivity activity;
    private Context context;

    private Button btnCamera, btnSaveChanges;
    private ImageView btnClosePicture, ivImg;

    private String pfp;

    public ChangePictureFragment() { super(); }

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
        return inflater.inflate(R.layout.fragment_change_picture, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) getActivity();
        localStorage = activity.getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        checkExternalStoragePermission();

        btnClosePicture = view.findViewById(R.id.btnClosePicture);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnSaveChanges = view.findViewById(R.id.btnChangePicture);
        ivImg = view.findViewById(R.id.ivCameraImg);

        btnSaveChanges.setEnabled(false);

        btnClosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { activity.navigateTo(activity.mainFragments.get(0)); }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) { saveChanges(); }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void openCamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        ivImg.setImageBitmap(imageBitmap);
        pfp = "base64,"+getStringImage(imageBitmap);
        btnSaveChanges.setEnabled(true);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return encodedImage;
    }

    private void saveChanges()
    {
        int id = Integer.parseInt(localStorage.getString(getString(R.string.id_user), ""));
        ChangePicture data = new ChangePicture(id, pfp);

        Call<Integer> call = ApiAdapter.getApiService().changePicture(data);
        call.enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                if (response.isSuccessful())
                {
                    Log.d(TAG, "El perfil ha sido actualizado");
                    activity.navigateTo(activity.mainFragments.get(0));
                } else
                {
                    Log.d(TAG, "No se puedo actualizar el usuario con id: " + id);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {

            }
        });
    }

    private void checkExternalStoragePermission()
    {
        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {

            } else
            {
                ActivityCompat.requestPermissions(activity, new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
            }
        }

        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CAMERA))
            {

            } else
            {
                ActivityCompat.requestPermissions(activity, new String[]
                        {Manifest.permission.CAMERA}, 226);
            }
        }

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        {
            if(activity.checkSelfPermission(Manifest.permission.CAMERA) !=  PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }
}
