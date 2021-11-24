package com.example.almishop.mainFragments.ProfileFragment;

import android.content.Context;
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

import com.example.almishop.MainActivity;
import com.example.almishop.R;
import com.example.almishop.io.ApiAdapter;
import com.example.almishop.model.ChangePassword;
import com.example.almishop.model.Login;
import com.example.almishop.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends DialogFragment {

    private static String TAG = "PASSWORD CHANGE DIALOG";
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private MainActivity activity;
    private Context context;

    private EditText etOldPassword, etNewPassword, etRepeatNewPassword;
    private Button btnChangePassword;
    private ImageView btnCloseRegister;

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
        return inflater.inflate(R.layout.fragment_change_password, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        activity = (MainActivity) getActivity();

        activity.getLocation();

        btnCloseRegister = getView().findViewById(R.id.btnCloseRegister);
        btnChangePassword = getView().findViewById(R.id.btnChangePassword);
        etOldPassword = getView().findViewById(R.id.etOldPassword);
        etNewPassword = getView().findViewById(R.id.etNewPassword);
        etRepeatNewPassword = getView().findViewById(R.id.etRepeatNewPassword);

        btnCloseRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.navigateTo(activity.mainFragments.get(0));
            }
        });


        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = etOldPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                String repeatNewPassword = etRepeatNewPassword.getText().toString();

                if (!oldPassword.equals(newPassword) && newPassword.equals(repeatNewPassword) && !newPassword.equals("")) {
                    Log.d(TAG, "onClick: changePwd OK!");

                    Call<User> getUserCall = ApiAdapter.getApiService().getUserById(Integer.parseInt(localStorage.getString(getString(R.string.id_user), "")));
                    getUserCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            if (response.isSuccessful() && user.getEmail() != null) {
                                Log.d(TAG, "changePwd getUser OK!");

                                Login loginData = new Login(user.getEmail(), oldPassword);
                                Call<User> postLoginCall = ApiAdapter.getApiService().login(loginData);
                                postLoginCall.enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        User userLogin = response.body();
                                        if (response.isSuccessful() && userLogin.getEmail() != null) {
                                            Log.d(TAG, "changePwd login OK!");

                                            ChangePassword changePassword = new ChangePassword(userLogin.getId(), newPassword);
                                            Call<Integer> putChangePasswordCall = ApiAdapter.getApiService().changePassword(changePassword);
                                            putChangePasswordCall.enqueue(new Callback<Integer>() {
                                                @Override
                                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                    if (response.isSuccessful() && response.body() == 200) {
                                                        Log.d(TAG, "PASSWORD CHANGED DONE! - SERVICE TERMINATED");

                                                        activity.navigateTo(activity.mainFragments.get(0));
                                                    } else if (response.body() != 200) {
                                                        Log.d(TAG, "changePwd changePwdCall KO - incorrect response (response != 200)");
                                                    } else {
                                                        Log.d(TAG, "changePwd changePwdCall KO - response NOT successful");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Integer> call, Throwable t) {
                                                    Log.d(TAG, "changePwd changePwdCall KO - " + t);
                                                }
                                            });
                                        } else if (userLogin.getEmail() != null) {
                                            Log.d(TAG, "changePwd login KO - response empty");
                                        } else {
                                            Log.d(TAG, "changePwd login KO - response NOT successful");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Log.d(TAG, "changePwd login KO - " + t);
                                    }
                                });
                            } else if (user.getEmail() != null) {
                                Log.d(TAG, "changePwd getUser KO - response empty");
                            } else {
                                Log.d(TAG, "changePwd getUser KO - response NOT successful");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d(TAG, "changePwd getUser KO - " + t);
                        }
                    });
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