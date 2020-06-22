package com.open.metrolauncher.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.models.App;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class LoadHomeTask extends AsyncTask<Context, Void, Void> {
    @Override
    protected Void doInBackground(Context... contexts) {
        Context context = contexts[0];
        try {
            FileInputStream fis = context.getApplicationContext().openFileInput("home_data");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(isr);

            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }

            reader.close();
            isr.close();
            fis.close();

            String ref = builder.toString();
            System.out.println("JSON Read => \r\n" + ref);

            final Gson g = new Gson();
            App[] array = g.fromJson(ref, App[].class);
            System.out.println("Length of values: " + array.length);
            if (array != null) {
                for (App info : array) {
                    System.out.println("Value found => " + info.getPackage());
                    Launcher.HOME_APPS.add(info);
                }
            }
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }

        return null;
    }
}
