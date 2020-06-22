package com.open.metrolauncher.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.models.App;
import com.open.metrolauncher.tasks.SaveHomeTask;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.lang.reflect.InvocationTargetException;

public class HomeDialog extends DialogFragment {
    App details;
    final String[] common_options = new String[] {
            "Remove from HomeScreen",
            "Swap Position",
            "Edit Icon",
            "Change size",
            "Details"
    };

    public HomeDialog(App details) {
        this.details = details;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());

        b.setTitle(details.getLabel());

        b.setItems(common_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0: // Remove
                        Launcher.HOME_APPS.remove(details);
                        Launcher.getHomeAdapter().notifyItemChanged(Launcher.HOME_APPS.size() - 1);
                        new SaveHomeTask().execute(getContext());
                        break;
                    case 1: // swap
                        break;
                    case 2: // edit icon
                        final ColorPicker colorPicker = new ColorPicker(getActivity());
                        colorPicker.show();
                        colorPicker.enableAutoClose();
                        colorPicker.setCallback(new ColorPickerCallback() {
                            @Override
                            public void onColorChosen(int color) {
                                details.setColor(color);
                                Launcher.getHomeAdapter().notifyDataSetChanged();
                                new SaveHomeTask().execute(Launcher.getContext());
                            }
                        });
                        break;
                    case 3: //change size
                        final AppDialog dialog = new AppDialog(details);
                        dialog.show(getFragmentManager(), "AppDialog");
                        break;
                    case 4: // details
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + details.getPackage()));
                        startActivity(intent);
                        break;
                }
            }
        });

        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        return b.create();
    }
}
