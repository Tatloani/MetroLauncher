package com.open.metrolauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;

import com.open.metrolauncher.adapters.FragmentsAdapter;
import com.open.metrolauncher.broadcasts.MyBroadcastReceiver;
import com.open.metrolauncher.fragments.Home;
import com.open.metrolauncher.tasks.LoadHomeTask;

public class MainActivity extends AppCompatActivity {
    ViewPager m_pager;
    Launcher m_launcher;
    MyBroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_launcher = Launcher.getInstance(getApplicationContext());
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        m_launcher.calc_size(p);

        m_pager = findViewById(R.id.viewpager);
        m_pager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));

        if (!m_launcher.isHasExisted()) {
            m_launcher.init();
            m_launcher.read_apps();
            new LoadHomeTask().execute(getApplicationContext());
        }
        RegisterBroadcast();
        //Launcher.ClearHome_Data();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(br);
        super.onDestroy();
    }

    private void RegisterBroadcast() {
        br = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(br, filter);
    }
}