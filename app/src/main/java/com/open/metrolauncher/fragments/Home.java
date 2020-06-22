package com.open.metrolauncher.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.R;
import com.open.metrolauncher.adapters.DrawerAdapter;
import com.open.metrolauncher.adapters.HomeAdapter;
import com.open.metrolauncher.models.App;

public class Home extends Fragment {
    RecyclerView rv;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance() {
        return new Home();
        //Home fragment = new Home();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        //return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Launcher.setHomeAdapter(new HomeAdapter(getFragmentManager()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);

        FlexboxLayoutManager mng = new FlexboxLayoutManager(getContext());
        mng.setJustifyContent(JustifyContent.FLEX_START);
        mng.setFlexDirection(FlexDirection.ROW);

        FlexboxItemDecoration x = new FlexboxItemDecoration(getContext());
        x.setDrawable(getContext().getDrawable(R.drawable.flex_divider));
        x.setOrientation(FlexboxItemDecoration.BOTH);

        rv = v.findViewById(R.id.recyclerLayout);
        rv.setLayoutManager(mng);
        rv.addItemDecoration(x);
        rv.setAdapter( Launcher.getHomeAdapter() );
        return v;
    }
}