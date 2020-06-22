package com.open.metrolauncher.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.open.metrolauncher.Launcher;

public class MyBroadcastReceiver extends BroadcastReceiver {
    Launcher l = Launcher.getInstace();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("EXTRA_REPLACING")
                && intent.getBooleanExtra("EXTRA_REPLACING", false)) {
            return;
        }
        System.out.println("INTENT RECEIVED");
        String package_name = intent.getData().toString().substring(8);
        System.out.println("DATA TOSTRING => " + package_name);
        System.out.println(intent.getAction());

        switch (intent.getAction()) {
            case "android.intent.action.PACKAGE_ADDED":
                l.notifyApp(package_name);
                break;
            case "android.intent.action.PACKAGE_REMOVED":
                l.removeApp(package_name);
                break;
        }
    }
}
