package com.yt.demo_view.DrawColor.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaveBitmapTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = SaveBitmapTask.class.getSimpleName();
    private static ArrayList<ImageSaveListener> listenrs = new ArrayList<>();
    private final Bitmap bitmap;
    private final Context context;
    private String key;
    private File file;

    public SaveBitmapTask(Context context, Bitmap bitmap, String url) {
        this.context = context.getApplicationContext();
        this.bitmap = Bitmap.createBitmap(bitmap);
        this.key = url;
        if (!TextUtils.isEmpty(key)) {
            file = new File(context.getFilesDir(), "star_" + System.currentTimeMillis() + ".png");
        }
    }

    public SaveBitmapTask(Context context, Bitmap bitmap, File file) {
        this.context = context.getApplicationContext();
        this.bitmap = Bitmap.createBitmap(bitmap);
        this.file = file;
    }

    public static void addListener(ImageSaveListener listener) {
        if (!listenrs.contains(listener)) {
            listenrs.add(listener);
        }
    }

    public static void removeListener(ImageSaveListener listener) {
        if (listenrs.contains(listener)) {
            listenrs.remove(listener);
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        for (ImageSaveListener listener : listenrs) {
            listener.imageSaved(file);
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (file.exists()) {
            if (file.delete()) {
                Log.e(TAG, "delete file suc.");
            } else {
                Log.e(TAG, "delete file failed.");
            }
            System.gc();
        }
        try {
            if (!file.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            return bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public interface ImageSaveListener {
        void imageSaved(File file);
    }
}
