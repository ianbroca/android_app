package com.example.almishop.mainFragments.ProfileFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
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
import androidx.fragment.app.Fragment;

import com.example.almishop.DatePickerFragment;
import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.model.ChangeProfile;
import com.example.almishop.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileFragment extends Fragment
{
    private static String TAG = "PROFILE DIALOG";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private MainActivity activity;
    private Context context;
    private ImageView btnClose;
    private EditText etName, etSurname1, etSurname2, etBirthdate;
    private Button btnSaveChanges;

    public ChangeProfileFragment() { super(); }

    @Override
    public void onAttach(Context context)
    {
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
        return inflater.inflate(R.layout.fragment_change_profile, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        activity = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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
                        etName.setText(user.getName());
                        etSurname1.setText(user.getSurname1());
                        etSurname2.setText(user.getSurname2());
                        etBirthdate.setText(user.getBirthdate().replace('-', '/'));
                    } else
                    {
                        Log.d(TAG, "No se puedo obtener el usuario con id: " + id);
                    }
                } catch (Exception ex) {
                    builder.setTitle("Error obtener usuario");
                    builder.setMessage("Por favor, inténtelo más tarde.");
                    builder.setPositiveButton("Aceptar", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Log.d(TAG, "ERROR AL OBTENER EL USUARIO");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        btnClose = view.findViewById(R.id.btnCloseProfile);
        etName = view.findViewById(R.id.etProfileName);
        etSurname1 = view.findViewById(R.id.etProfileSurname1);
        etSurname2 = view.findViewById(R.id.etProfileSurname2);
        etBirthdate = view.findViewById(R.id.etProfileBirthdate);
        btnSaveChanges = view.findViewById(R.id.btnChangeProfile);

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
                activity.navigateTo(activity.mainFragments.get(0));
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = etName.getText().toString();
                String surname1 = etSurname1.getText().toString();
                String surname2 = etSurname2.getText().toString();
                String birthdate = etBirthdate.getText().toString();
                ChangeProfile data = new ChangeProfile(id, name, surname1, surname2, birthdate);

                Log.d(TAG, "DATA: " + name + " " + surname1 + " " + surname2 + " " + birthdate);
                if (!name.trim().equals("") && !surname1.trim().equals("") && !birthdate.equals("")) {
                    Call<Integer> call = ApiAdapter.getApiService().changeProfile(data);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            try {
                                if (response.isSuccessful())
                                {
                                    int res = response.body();
                                    localStorageEditor.putString(getString(R.string.id_user), "" + id).commit();
                                    activity.navigateTo(activity.mainFragments.get(0));
//                                    ProfileDialogFragment profileDialogFragment = (ProfileDialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Profile");
//                                    profileDialogFragment.dismiss();
//                                    dismiss();
                                } else
                                {
                                    builder.setTitle("Error al guardar los cambios");
                                    builder.setMessage("Por favor, inténtelo más tarde.");
                                    builder.setPositiveButton("Aceptar", null);

                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                    Log.d(TAG, "Error al guardar los cambios.");
                                }
                            } catch (Exception ex) {
                                builder.setTitle("Error de servidor interno");
                                builder.setMessage("Por favor, inténtelo más tarde.");
                                builder.setPositiveButton("Aceptar", null);

                                AlertDialog dialog = builder.create();
                                dialog.show();
                                Log.d(TAG, "PROFILE ERROR");
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t)
                        {

                        }
                    });
                } else {
                    builder.setTitle("Error");
                    builder.setMessage("Hay campos vacíos, por favor rellénelos.");
                    builder.setPositiveButton("Aceptar", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Log.e(TAG, "onClick: KO - Campos vacíos");
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
