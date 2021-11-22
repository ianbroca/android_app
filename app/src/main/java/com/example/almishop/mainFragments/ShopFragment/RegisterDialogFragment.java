package com.example.almishop.mainFragments.ShopFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.R;

public class RegisterDialogFragment extends DialogFragment
{
    private static String TAG = "ProfileDialogFragment";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private ImageView btnClose;
    private EditText etEmail, etPassword, etRepassword, etName, etSurname1, etSurname2;
    private Button btnRegister;

    public RegisterDialogFragment()
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
        view = inflater.inflate(R.layout.fragment_dialog_register, container, false);

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
        btnClose = view.findViewById(R.id.btnCloseRegister);
        etEmail = view.findViewById(R.id.etRegisterEmail);
        etPassword = view.findViewById(R.id.etRegisterPassword);
        etRepassword = view.findViewById(R.id.etRegisterRepassword);
        etName = view.findViewById(R.id.etRegisterName);
        etSurname1 = view.findViewById(R.id.etRegisterSurname1);
        etSurname2 = view.findViewById(R.id.etRegisterSurname2);
        // FALTA LA FECHA DE NACIMIENTO, PERO NO SE HACE CON EDIT TEXT ////////////////////////////
        btnRegister = view.findViewById(R.id.btnRegister);

        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // LLAMADA REGISTER
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
