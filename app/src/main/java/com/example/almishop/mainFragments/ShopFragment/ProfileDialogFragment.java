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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.ListAdapter;
import com.example.almishop.R;

import java.util.ArrayList;

public class ProfileDialogFragment extends DialogFragment
{
    private static String TAG = "ProfileDialogFragment";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private ListView listView;
    private ImageView btnClose;
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
        localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();

        ArrayList<String> listElements = new ArrayList<String>();
        if (localStorage.getString(getString(R.string.id_user), "") != "")
        {
            listElements.add("Editar información personal");
            listElements.add("Cambiar foto de perfil");
            listElements.add("Cambiar contraseña");
            Log.d("TAG", "Encontrado: " + localStorage.getString(getString(R.string.id_user), ""));
            view = inflater.inflate(R.layout.fragment_dialog_profile, container, false);
            btnClose = view.findViewById(R.id.btnCloseProfile);
            btnLogout = view.findViewById(R.id.btnLogin);
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
                        case 0: // Navegar a información personal
                            Log.d(TAG, "onItemClick: Navigate to personal info");
                            break;
                        case 1: // Cambiar foto de perfil
                            Log.d(TAG, "onItemClick: Navigate to change propile picture");
                            break;
                        case 2: // Cambiar password
                            Log.d(TAG, "onItemClick: Navigate to change password");
                            break;
                    }
                } else
                {
                    switch (i)
                    {
                        case 0: // Iniciar sesión
                            Log.d(TAG, "onItemClick: Navigate to login");
                            localStorageEditor.putString(getString(R.string.id_user), "1").commit();
                            break;
                        case 1: // Registrarse
                            Log.d(TAG, "onItemClick: Navigate to register");
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
