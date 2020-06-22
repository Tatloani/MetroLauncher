package com.open.metrolauncher.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.R;
import com.open.metrolauncher.dialogs.DrawerDialog;
import com.open.metrolauncher.dialogs.HomeDialog;
import com.open.metrolauncher.models.App;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    private FragmentManager m_manager;

    public DrawerAdapter(FragmentManager manager) {
        m_manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_drawer_app, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setApp( Launcher.GLOBAL_APPS.get(position) );
    }

    @Override
    public int getItemCount() {
        return Launcher.GLOBAL_APPS.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtApp;
        ConstraintLayout layoutApp;
        ImageView imgApp;
        private App details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutApp = itemView.findViewById(R.id.layoutApp);
            txtApp = itemView.findViewById(R.id.txtApp);
            imgApp = itemView.findViewById(R.id.imgApp);
        }

        public void setApp(App details) {
            this.details = details;
            txtApp.setText(this.details.getLabel());
            imgApp.setImageDrawable(Launcher.getDrawableFromApp(this.details.getPackage()));
            //imgApp.setBackgroundColor(Color.DKGRAY);
            layoutApp.setOnClickListener(this);
            layoutApp.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Launcher.startActivity(details);
        }

        @Override
        public boolean onLongClick(View view) {
            new DrawerDialog(details).show(m_manager, "DrawerDialog");
            return true;
        }
    }
}
