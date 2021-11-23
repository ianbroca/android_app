package com.example.almishop.mainFragments.ProfileFragment;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.DatePickerFragment;
import com.example.almishop.ListAdapter;
import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.model.User;

import retrofit2.Call;

public class ChangePasswordFragment extends DialogFragment {

    private static String TAG = "PASSWORD CHANGE DIALOG";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private MainActivity activity;
    private Context context;

    private EditText etOldPassword, etNewPassword, etRepeatNewPassword;
    private Button btnChangePassword;

    public ChangePasswordFragment() { super(); }

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
        return inflater.inflate(R.layout.fragment_dialog_change_password, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        activity = (MainActivity) getActivity();

        btnChangePassword = getView().findViewById(R.id.btnChangePassword);
        etOldPassword = getView().findViewById(R.id.etOldPassword);
        etNewPassword = getView().findViewById(R.id.etNewPassword);
        etRepeatNewPassword = getView().findViewById(R.id.etRepeatNewPassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = etOldPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                String repeatNewPassword = etRepeatNewPassword.getText().toString();

                if (!oldPassword.equals(newPassword) && newPassword.equals(repeatNewPassword) && !newPassword.equals("")) {
                    Log.d(TAG, "onClick: changePwd OK!");

                    Call<User> call = ApiAdapter.getApiService().getUserById(localStorage.getString(getString(R.string.id_user), ""););
                } else if (oldPassword.equals("") || newPassword.equals("") || repeatNewPassword.equals("")) {
                    Log.d(TAG, "onClick: changePWD KO - empty fields");
                } else if (oldPassword.equals(newPassword)) {
                    Log.d(TAG, "onClick: changePWD KO - oldPwd = newPwd");
                } else {
                    Log.d(TAG, "onClick: changePWD KO - newPwd != repNewPwd");
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}