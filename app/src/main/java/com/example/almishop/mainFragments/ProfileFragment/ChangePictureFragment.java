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
    private ImageView ivImg;

    private Uri imageUri;
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

        btnCamera = view.findViewById(R.id.btnCamera);
        btnSaveChanges = view.findViewById(R.id.btnChangePicture);
        ivImg = view.findViewById(R.id.ivCameraImg);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void openCamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(activity.getPackageManager())!= null)
        {
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            } catch(IOException exception)
            {

            }

            if(photoFile != null)
            {
                ContentValues values = new ContentValues();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = dateFormat.format(new Date());
                String imgTitle = "Picture" + date + ".jpg";

                values.put(MediaStore.Images.Media.TITLE, imgTitle);
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                imageUri = activity.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
                updateImageView();
            }
        }
    }

    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    private void updateImageView()
    {
        Glide
                .with(context)
                .load(new File(imageUri.getPath()))
                .into(ivImg);
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
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