package com.example.almishop.mainFragments.ShopFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.almishop.ListAdapter;
import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.mainFragments.ProfileFragment.RegisterFragment;
import com.example.almishop.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDialogFragment extends DialogFragment
{
    private static String TAG = "ProfileDialogFragment";
    private DialogFragment dialog;
    private MainActivity activity;

    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private ListView listView;
    private ImageView btnClose;
    private TextView tvName = null, tvBirthdate = null;
    private ImageView ivPfp = null;
    private Button btnLogout = null;

    public ProfileDialogFragment()
    {
        super();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Login");
        return dialog;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog)
    {
        super.onCancel(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view;
        activity = (MainActivity) getActivity();
        localStorage = activity.getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();

        ArrayList<String> listElements = new ArrayList<String>();
        if (localStorage.getString(getString(R.string.id_user), "") != "")
        {
            listElements.add("Historial de compras");
            listElements.add("Editar información personal");
            listElements.add("Cambiar foto de perfil");
            listElements.add("Cambiar contraseña");
            Log.d("TAG", "Encontrado: " + localStorage.getString(getString(R.string.id_user), ""));
            view = inflater.inflate(R.layout.fragment_dialog_profile, container, false);
            btnClose = view.findViewById(R.id.btnCloseProfile);
            btnLogout = view.findViewById(R.id.btnLogin);
            ivPfp = view.findViewById(R.id.ivProfileImg);
            tvName = view.findViewById(R.id.tvProfileName);
            tvBirthdate = view.findViewById(R.id.tvProfileBirthdate);
        } else
        {
            listElements.add("Iniciar sesión");
            listElements.add("Registrarse");
            Log.d("TAG", "No hay id: " + localStorage.getString(getString(R.string.id_user), ""));
            view = inflater.inflate(R.layout.fragment_dialog_logreg, container, false);
            btnClose = view.findViewById(R.id.btnCloseLogReg);
        }
        ListAdapter adapter = new ListAdapter(getContext(), listElements);
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        if (ivPfp != null)
        {
            int id = Integer.parseInt(localStorage.getString(getString(R.string.id_user), ""));
            Call<User> call = ApiAdapter.getApiService().getUserById(id);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    try {
                        if (response.isSuccessful())
                        {
                            User user = response.body();
                            Log.d(TAG, "Obtenido usuario: " + user.getName() + " " + user.getSurname1() + " " + user.getSurname2());
                            Glide
                                    .with(getContext())
                                    .load(user.getPfp())
                                    .centerCrop()
                                    .into(ivPfp);
                            tvName.setText(user.getName() + " " + user.getSurname1() + " " + user.getSurname2());
                            tvBirthdate.setText(user.getBirthdate());
                        } else
                        {
                            Log.d(TAG, "No se puedo obtener el usuario con id: " + id);
                        }
                    } catch (Exception ex) {
                        Log.d(TAG, "ERROR AL OBTENER EL USUARIO");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (localStorage.getString(getString(R.string.id_user), "") != "")
                {
                    switch (i)
                    {
                        case 0: // Navegar a historial de compras
                            Log.d(TAG, "onItemClick: Navigate to buy history");
                            break;
                        case 1: // Navegar a información personal
                            Log.d(TAG, "onItemClick: Navigate to personal info");
                            break;
                        case 2: // Cambiar foto de perfil
                            Log.d(TAG, "onItemClick: Navigate to change propile picture");
                            break;
                        case 3: // Cambiar password
                            Log.d(TAG, "onItemClick: Navigate to change password");
                            activity.navigateTo(activity.changePasswordFragment);
                            dismiss();
                            break;
                    }
                } else
                {
                    switch (i)
                    {
                        case 0: // Iniciar sesión
                            Log.d(TAG, "onItemClick: Navigate to login");
                            dialog = new LoginDialogFragment();
                            dialog.show(getActivity().getSupportFragmentManager(), "Login");
                            //localStorageEditor.putString(getString(R.string.id_user), "1").commit();
                            break;
                        case 1: // Registrarse
                            Log.d(TAG, "onItemClick: Navigate to register");
                            activity.navigateTo(activity.registerFragment);
                            dismiss();
//                            dialog = new RegisterDialogFragment();
//                            dialog.show(getActivity().getSupportFragmentManager(), "Register");
                            break;
                    }
                }
            }
        });
        if (btnLogout != null)
        {
            btnLogout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    localStorageEditor.putString(getString(R.string.id_user), "").commit();
                    dismiss();
                }
            });
        }
        super.onViewCreated(view, savedInstanceState);
    }
}
