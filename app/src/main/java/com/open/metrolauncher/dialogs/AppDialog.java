package com.open.metrolauncher.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.R;
import com.open.metrolauncher.models.App;
import com.open.metrolauncher.tasks.SaveHomeTask;

public class AppDialog extends DialogFragment {
    SeekBar seek1;
    SeekBar seek2;
    App details;

    public AppDialog(App child) {
        details = child;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_size, null);
        seek1 = v.findViewById(R.id.picker1);
        seek2 = v.findViewById(R.id.picker2);
        seek1.setMax(Launcher.START_LENGTH);
        seek2.setMax(Launcher.TOP_LENGTH);
        seek1.setMin(1);
        seek2.setMin(1);
        b.setView(v);
        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                details.setH_unit(seek1.getProgress());
                details.setV_unit(seek2.getProgress());
                Launcher.getHomeAdapter().notifyDataSetChanged();
                new SaveHomeTask().execute(Launcher.getContext());
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return b.create();
    }
}
