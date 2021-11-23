package com.example.almishop.mainFragments.ProfileFragment;

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
import com.example.almishop.model.Register;
import com.example.almishop.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileFragment extends Fragment
{
    private static String TAG = "REGISTER DIALOG";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private MainActivity activity;
    private Context context;
    private ImageView btnClose;
    private EditText etName, etSurname1, etSurname2, etBirthdate;
    private Button btnRegister;

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

        btnClose = view.findViewById(R.id.btnCloseRegister);
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
                activity.navigateTo(activity.mainFragments.get(0));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = etName.getText().toString();
                String surname1 = etSurname1.getText().toString();
                String surname2 = etSurname2.getText().toString();
                String birthdate = etBirthdate.getText().toString();
//                Register data = new Register(name, surname1, surname2, birthdate);

//                Call<User> call = ApiAdapter.getApiService().register(data);
//                call.enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        try {
//                            if (response.isSuccessful())
//                            {
//                                User user = response.body();
//                                Log.d(TAG, "Register successful: " + user.getName() + " " + user.getSurname1() + " " + user.getSurname2());
//                                localStorageEditor.putString(getString(R.string.id_user), "" + user.getId()).commit();
//                                getActivity().getSupportFragmentManager().popBackStackImmediate();
////                                    ProfileDialogFragment profileDialogFragment = (ProfileDialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Profile");
////                                    profileDialogFragment.dismiss();
////                                    dismiss();
//                            } else
//                            {
//                                Log.d(TAG, "Register failed. Username: " + email + " Password: " + password);
//                            }
//                        } catch (Exception ex) {
//                            Log.d(TAG, "REGISTER ERROR");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//
//                    }
//                });
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
