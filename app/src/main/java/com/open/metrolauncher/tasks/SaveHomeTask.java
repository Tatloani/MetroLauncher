package com.open.metrolauncher.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.open.metrolauncher.Launcher;
import com.open.metrolauncher.models.App;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaveHomeTask extends AsyncTask<Context, Void, Void> {
    @Override
    protected Void doInBackground(Context... contexts) {
        Context context = contexts[0];
        final Gson gson = new Gson();
        String json = gson.toJson(Launcher.HOME_APPS.toArray(), App[].class);
        System.out.println("JSON Saved => \r\n" + json);

        try (FileOutputStream fos = context.openFileOutput("home_data", Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
