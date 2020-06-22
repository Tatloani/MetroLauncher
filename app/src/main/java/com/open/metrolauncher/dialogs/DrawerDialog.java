package com.open.metrolauncher.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.models.App;
import com.open.metrolauncher.tasks.SaveHomeTask;

public class DrawerDialog extends DialogFragment {
    App details;
    final String[] system_options = new String[] {
            "Send to Homescreen",
            "Details"
    };

    final String[] common_options = new String[] {
            "Unistall",
            "Send to Homescreen",
            "Details"
    };

    public DrawerDialog(App details) {
        this.details = details;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());

        b.setTitle(details.getLabel());

        if (details.isSystem()) {
            b.setItems(system_options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0: // send to homescreen
                            Launcher.HOME_APPS.add(details);
                            Launcher.getHomeAdapter().notifyItemInserted(Launcher.HOME_APPS.size() - 1);
                            new SaveHomeTask().execute(getContext());
                            break;
                        case 1: // details
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + details.getPackage()));
                            startActivity(intent);
                            break;
                    }
                }
            });
        } else {
            b.setItems(common_options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0: // unistall
                            final Intent I = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + details.getPackage()));
                            startActivity(I);
                            break;
                        case 1: // send to homescreen
                            Launcher.HOME_APPS.add(details);
                            Launcher.getHomeAdapter().notifyItemInserted(Launcher.HOME_APPS.size() - 1);
                            new SaveHomeTask().execute(getContext());
                            break;
                        case 2: // details
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + details.getPackage()));
                            startActivity(intent);
                            break;
                    }
                }
            });
        }

        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        return b.create();
    }
}
