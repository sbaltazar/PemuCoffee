package com.sbaltazar.pemucoffee.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sbaltazar.pemucoffee.R;

public class NoInternetDialogFragment extends DialogFragment {

    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext); //, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert);

        builder.setMessage(R.string.dialog_no_internet_message)
                .setPositiveButton(R.string.dialog_button_internet_configuration, (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.dialog_button_cancel, (dialog, which) -> {
                    dialog.dismiss();
                    ((Activity)mContext).finish();
                });

        return builder.create();
    }

}
