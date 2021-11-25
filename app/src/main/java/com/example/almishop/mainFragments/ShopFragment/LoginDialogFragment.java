package com.example.almishop.mainFragments.ShopFragment;

import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.model.Login;
import com.example.almishop.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDialogFragment extends DialogFragment
{
    private static String TAG = "LOGIN DIALOG";
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
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        btnClose = view.findViewById(R.id.btnCloseLogin);
        etEmail = view.findViewById(R.id.etLoginEmail);
        etPassword = view.findViewById(R.id.etLoginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                Login data = new Login(email, password);
                if (!email.trim().equals("") && !password.equals("")) {
                    Call<User> call = ApiAdapter.getApiService().login(data);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            try {
                                if (response.isSuccessful())
                                {
                                    User user = response.body();
                                    if (user.getId() != 0)
                                    {
                                        Log.d(TAG, "login OK!: " + user.getId() + " " + user.getName() + " " + user.getSurname1() + " " + user.getSurname2());
                                        localStorageEditor.putString(getString(R.string.id_user), "" + user.getId()).commit();
                                        ProfileDialogFragment profileDialogFragment = (ProfileDialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Profile");
                                        profileDialogFragment.dismiss();
                                        dismiss();
                                    } else
                                    {
                                        builder.setTitle("Error al iniciar sesión");
                                        builder.setMessage("El correo y contraseña introducidos no coinciden.");
                                        builder.setPositiveButton("Aceptar", null);

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        Log.d(TAG, "login KO - El correo y contraseña introducidos no coinciden.");
                                    }
                                } else
                                {
                                    builder.setTitle("Error al iniciar sesión");
                                    builder.setMessage("No se pudo iniciar sesión.");
                                    builder.setPositiveButton("Aceptar", null);

                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                    Log.d(TAG, "KO - Username: " + email + " Password: " + password);
                                }
                            } catch (Exception ex) {
                                builder.setTitle("Error al iniciar sesión");
                                builder.setMessage("Error interno de servidor, por favor inténtelo más tarde.");
                                builder.setPositiveButton("Aceptar", null);

                                AlertDialog dialog = builder.create();
                                dialog.show();
                                Log.d(TAG, "LOGIN ERROR" + ex);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                } else {
                    builder.setTitle("Error");
                    builder.setMessage("Hay campos vacíos, por favor rellénelos.");
                    builder.setPositiveButton("Aceptar", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Log.e(TAG, "login onClick: KO - empty fields");
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
