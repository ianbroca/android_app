package com.example.almishop.mainFragments.ProfileFragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import androidx.fragment.app.Fragment;

import com.example.almishop.MainActivity;
import com.example.almishop.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        return inflater.inflate(R.layout.fragment_change_password, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        activity = (MainActivity) getActivity();

        btnCamera = view.findViewById(R.id.btnCamera);
        btnSaveChanges = view.findViewById(R.id.btnChangePicture);
        ivImg = view.findViewById(R.id.ivCameraImg);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                        values.put(MediaStore.Images.Media.TITLE, "Picture" + date + ".jpg" );
                        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                        imageUri = activity.getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
                        InputStream imageStream = null;
                        try {
                            imageStream = activity.getContentResolver().openInputStream(imageUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        String encodedImage = encodeImage(selectedImage);
                    }
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }
}
