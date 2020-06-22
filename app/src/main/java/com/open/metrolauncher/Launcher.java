package com.open.metrolauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.open.metrolauncher.adapters.DrawerAdapter;
import com.open.metrolauncher.adapters.HomeAdapter;
import com.open.metrolauncher.broadcasts.MyBroadcastReceiver;
import com.open.metrolauncher.models.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL;

public class Launcher {
    public final static int START_LENGTH = 3;
    public final static int TOP_LENGTH = 4;
    public final static int HEIGHT_OFFSET = -50;
    public final static int WIDTH_OFFSET = -30;
    public static int SIZE_TOP = 0;
    public static int SIZE_START = 0;
    public static int DEFAULT_COLOR = 0;

    public static ArrayList<App> GLOBAL_APPS = new ArrayList<>();
    public static ArrayList<App> HOME_APPS = new ArrayList<>();

    private static Context m_context;
    private static DrawerAdapter m_drawer_adapter;
    private static HomeAdapter m_home_adapter;

    private static Launcher _THIS_;
    public static boolean hasExisted = false;

    private Launcher(Context main_context) {
        m_context = main_context;
    }

    public static Launcher getInstance(Context context) {
        if (_THIS_ == null) {
            _THIS_ = new Launcher(context);
        }
        return _THIS_;
    }

    public static Launcher getInstace() {
        return _THIS_;
    }

    public static Context getContext() {
        return m_context;
    }

    public void calc_size(Point p) {
        Launcher.SIZE_TOP = p.y / Launcher.TOP_LENGTH + Launcher.HEIGHT_OFFSET;
        Launcher.SIZE_START = p.x / Launcher.START_LENGTH + Launcher.WIDTH_OFFSET;
    }

    public void read_apps() {
        final PackageManager pm = m_context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ri = pm.queryIntentActivities(i, 0);
        for(ResolveInfo r : ri) {
            final App e = new App(r.activityInfo.packageName, r.activityInfo.applicationInfo.flags);
            e.setLabel(r.loadLabel(pm));
            e.setIcon( r.loadIcon(pm) );

            GLOBAL_APPS.add(e);
        }
    }

    public static DrawerAdapter getDrawerAdapter() {
        return m_drawer_adapter;
    }

    public static HomeAdapter getHomeAdapter() {
        return m_home_adapter;
    }

    public static void setHomeAdapter(HomeAdapter adapter) {
        m_home_adapter = adapter;
    }

    public static void setDrawerAdapter(DrawerAdapter adapter) {
        m_drawer_adapter = adapter;
    }

    public static Drawable getDrawableFromApp(String package_name) {
        for (App e : GLOBAL_APPS) {
            if (e.toString().equals(package_name)) {
                return e.getIcon();
            }
        }
        return null;
    }

    public static void startActivity(App app) {
        m_context.startActivity(m_context.getPackageManager().getLaunchIntentForPackage(app.getPackage()));
    }

    public void init() {
        try {
            File f = new File(m_context.getFilesDir(), "home_data");
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
        hasExisted = true;
        DEFAULT_COLOR = m_context.getColor(R.color.colorPrimary);
    }

    public static boolean isHasExisted() {
        return hasExisted;
    }

    public static void ClearHome_Data() {
        try (FileOutputStream fos = m_context.openFileOutput("home_data", Context.MODE_PRIVATE)) {
            fos.write("".getBytes());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void removeApp(String package_name) {
        int id = -1;
        boolean home_changed = false;
        for (int i = 0; i < GLOBAL_APPS.size(); i++) {
            if (GLOBAL_APPS.get(i).getPackage().equals(package_name)) {
                App info = GLOBAL_APPS.remove(i);
                home_changed = HOME_APPS.remove(info);
                id = i;
            }
        }
        m_drawer_adapter.notifyItemRemoved(id);
        if (home_changed) m_home_adapter.notifyDataSetChanged();
    }

    public void notifyApp(String package_name) {
        final PackageManager pm = m_context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ri = pm.queryIntentActivities(i, 0);
        for(ResolveInfo r : ri) {
            final App e = new App(r.activityInfo.packageName, r.activityInfo.flags);
            if (e.getPackage().equals(package_name)) {
                e.setLabel(r.loadLabel(pm));
                e.setIcon( r.loadIcon(pm) );

                GLOBAL_APPS.add(e);
            } else {
                continue;
            }
        }
        m_drawer_adapter.notifyItemInserted(GLOBAL_APPS.size() - 1);
    }
}
