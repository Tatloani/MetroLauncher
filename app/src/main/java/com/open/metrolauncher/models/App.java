package com.open.metrolauncher.models;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.R;

public class App {
    private final String PACKAGE_NAME;
    private String label;
    private transient Drawable icon;
    private int v_unit;
    private int h_unit;
    private final int flags;
    private int color = Launcher.DEFAULT_COLOR;

    public String getPackage() {
        return PACKAGE_NAME;
    }

    public void setLabel(CharSequence label) {
        this.label = label.toString();
    }

    public App(String PACKAGE_NAME, int v_unit, int h_unit, int flags) {
        this.PACKAGE_NAME = PACKAGE_NAME;
        this.v_unit = v_unit;
        this.h_unit = h_unit;
        this.flags = flags;
    }

    public App(String PACKAGE_NAME, int flags) {
        this.PACKAGE_NAME = PACKAGE_NAME;
        this.v_unit = 1;
        this.h_unit = 1;
        this.flags = flags;
    }

    public void setH_unit(int h_unit) {
        this.h_unit = h_unit;
    }

    public void setV_unit(int v_unit) {
        this.v_unit = v_unit;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getWidth() {
        return Launcher.SIZE_START * h_unit;
    }

    public int getHeight() {
        return Launcher.SIZE_TOP * v_unit;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public int getH_unit() {
        return h_unit;
    }

    public int getV_unit() {
        return v_unit;
    }

    public int getFlags() {
        return flags;
    }

    public CharSequence getLabel() {
        return label;
    }

    @NonNull
    @Override
    public String toString() {
        return PACKAGE_NAME;
    }

    public boolean isSystem() {
        return (flags & ApplicationInfo.FLAG_SYSTEM) != 0 || (flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0;
    }
}
