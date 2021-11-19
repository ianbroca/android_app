package com.example.almishop.mainFragments.ShopFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.almishop.R;

public class ProfileDialogFragment extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        SharedPreferences localStorage = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor localStorageEditor = localStorage.edit();
        if (localStorage.getString(getString(R.string.id_user), "") != "")
        {
            Log.d("TAG", "Encontrado: " + localStorage.getString(getString(R.string.id_user), ""));
        } else
        {
            Log.d("TAG", "No hay id: " + localStorage.getString(getString(R.string.id_user), ""));
        }
        //localStorageEditor.putString(getString(R.string.id_user), id);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("a");
        return super.onCreateDialog(savedInstanceState);
    }
}
