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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.ListAdapter;
import com.example.almishop.R;

import java.util.ArrayList;

public class LoginDialogFragment extends DialogFragment
{
    private static String TAG = "ProfileDialogFragment";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private ImageView btnClose;
    private EditText etEmail, etPassword;
    private Button btnLogin;

    public LoginDialogFragment()
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
        view = inflater.inflate(R.layout.fragment_dialog_login, container, false);

        if (localStorage.getString(getString(R.string.id_user), "") != "")
        {
        } else
        {
            dismiss();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        btnClose = view.findViewById(R.id.btnCloseLogin);
        etEmail = view.findViewById(R.id.etLoginEmail);
        etPassword = view.findViewById(R.id.etLoginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // LLAMADA LOGIN
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
