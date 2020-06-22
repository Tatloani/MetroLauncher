package com.open.metrolauncher.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.R;
import com.open.metrolauncher.dialogs.HomeDialog;
import com.open.metrolauncher.models.App;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private FragmentManager m_manager;

    public HomeAdapter(FragmentManager manager) {
        m_manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_home_app, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setApp( Launcher.HOME_APPS.get(position) );
    }

    @Override
    public int getItemCount() {
        return Launcher.HOME_APPS.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgApp;
        ConstraintLayout layoutApp;
        private App details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgApp = itemView.findViewById(R.id.imgApp);
            layoutApp = itemView.findViewById(R.id.layoutApp);
        }

        public void setApp(App details) {
            this.details = details;
            imgApp.setImageDrawable(Launcher.getDrawableFromApp(this.details.getPackage()));
            layoutApp.setOnClickListener(this);
            layoutApp.setOnLongClickListener(this);
            imgApp.setMinimumWidth(Launcher.SIZE_START);
            imgApp.setMinimumHeight(Launcher.SIZE_TOP);
            layoutApp.setMinWidth(details.getWidth());
            layoutApp.setBackgroundColor(details.getColor());
        }

        @Override
        public void onClick(View view) {
            Launcher.startActivity(details);
        }

        @Override
        public boolean onLongClick(View view) {
            new HomeDialog(details).show(m_manager, "HomeDialog");
            return true;
        }
    }
}
