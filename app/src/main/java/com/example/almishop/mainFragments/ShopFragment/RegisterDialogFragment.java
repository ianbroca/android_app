package com.example.almishop.mainFragments.ShopFragment;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.DatePickerFragment;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.model.Register;
import com.example.almishop.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDialogFragment extends DialogFragment
{
    private static String TAG = "REGISTER DIALOG";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private ImageView btnClose;
    private EditText etEmail, etPassword, etRepassword, etName, etSurname1, etSurname2, etBirthdate;
    private Button btnRegister;

    public RegisterDialogFragment()
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
        view = inflater.inflate(R.layout.fragment_dialog_register, container, false);
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
        etBirthdate = view.findViewById(R.id.etRegisterBirthdate);
        btnRegister = view.findViewById(R.id.btnRegister);

        etBirthdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d)
                    {
                        m = m+1;
                        String year, month, day;
                        year = "" + y;
                        if (m < 10)
                        {
                            month = "0" + m;
                        } else
                        {
                            month = "" + m;
                        }
                        if (d < 10)
                        {
                            day = "0" + d;
                        } else
                        {
                            day = "" + d;
                        }
                        final String selectedDate = year + "/" + month + "/" + day;
                        Log.d(TAG, "onDateSet: "+selectedDate);
                        etBirthdate.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

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
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String repassword = etRepassword.getText().toString();
                String name = etName.getText().toString();
                String surname1 = etSurname1.getText().toString();
                String surname2 = etSurname2.getText().toString();
                String birthdate = etBirthdate.getText().toString();
                Register data = new Register(email, password, name, surname1, surname2, birthdate);

                if (password.equals(repassword))
                {
                    Call<User> call = ApiAdapter.getApiService().register(data);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            try {
                                if (response.isSuccessful())
                                {
                                    User user = response.body();
                                    Log.d(TAG, "Register successful: " + user.getName() + " " + user.getSurname1() + " " + user.getSurname2());
                                    localStorageEditor.putString(getString(R.string.id_user), "" + user.getId()).commit();
                                    ProfileDialogFragment profileDialogFragment = (ProfileDialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Profile");
                                    profileDialogFragment.dismiss();
                                    dismiss();
                                } else
                                {
                                    Log.d(TAG, "Register failed. Username: " + email + " Password: " + password);
                                }
                            } catch (Exception ex) {
                                Log.d(TAG, "REGISTER ERROR");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                } else
                {
                    Log.d(TAG, "onClick: Las contraseñas no coinciden");
                    // DIALOGO DE ERROR PORQUE ERES GILIPOLLAS
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
