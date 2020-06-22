package com.open.metrolauncher.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.R;
import com.open.metrolauncher.adapters.DrawerAdapter;

public class Drawer extends Fragment {
    RecyclerView rv;

    public Drawer() {
        // Required empty public constructor
    }

    public static Drawer newInstance() {
        return new Drawer();
        //Drawer fragment = new Drawer();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        //return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Launcher.setDrawerAdapter(new DrawerAdapter(getFragmentManager()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        rv = v.findViewById(R.id.recyclerLayout);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter( Launcher.getDrawerAdapter() );
        return v;
    }
}