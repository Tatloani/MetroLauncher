package com.open.metrolauncher.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.open.metrolauncher.fragments.Drawer;
import com.open.metrolauncher.fragments.Home;

public class FragmentsAdapter extends FragmentStatePagerAdapter {
    private final static int NUM_PAGES = 2;

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public FragmentsAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Home.newInstance();
            case 1:
                return Drawer.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
