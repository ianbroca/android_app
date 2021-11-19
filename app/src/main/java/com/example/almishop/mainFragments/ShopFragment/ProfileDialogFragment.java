package com.example.almishop.mainFragments.ShopFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.R;

public class ProfileDialogFragment extends DialogFragment
{
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private Button btnLogin = null;
    private Button btnRegister = null;
    private Button btnProfile = null;
    private Button btnLogout = null;
    private Button btnClose;

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
        if (localStorage.getString(getString(R.string.id_user), "") != "")
        {
            Log.d("TAG", "Encontrado: " + localStorage.getString(getString(R.string.id_user), ""));
            view = inflater.inflate(R.layout.fragment_dialog_profile, container, false);
            btnClose = view.findViewById(R.id.btnCloseProfile);
            btnProfile = view.findViewById(R.id.btnNavigateToProfile);
            btnLogout = view.findViewById(R.id.btnLogout);
        } else
        {
            Log.d("TAG", "No hay id: " + localStorage.getString(getString(R.string.id_user), ""));
            view = inflater.inflate(R.layout.fragment_dialog_logreg, container, false);
            btnClose = view.findViewById(R.id.btnCloseLogReg);
            btnLogin = view.findViewById(R.id.btnNavigateToLogin);
            btnRegister = view.findViewById(R.id.btnNavigateToRegister);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        if (btnLogin != null)
        {
            btnLogin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    localStorageEditor.putString(getString(R.string.id_user), "1").commit();
                    Log.d("TAG", "onClick: " + localStorage.getString(getString(R.string.id_user), ""));
                    // ABRIR FRAGMENTO LOGIN
                    dismiss();
                }
            });
            btnRegister.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // ABRIR FRAGMENTO REGISTER
                    dismiss();
                }
            });
        } else
        {
            btnProfile.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // ABRIR FRAGMENTO PROFILE
                    dismiss();
                }
            });
            btnLogout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    localStorageEditor.putString(getString(R.string.id_user), "").commit();
                    Log.d("TAG", "onClick: " + localStorage.getString(getString(R.string.id_user), ""));
                    // ELIMINAR ID DE LOCALSTORAGE Y RECARGAR FRAGMENTO
                    dismiss();
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
        super.onViewCreated(view, savedInstanceState);
    }
}
