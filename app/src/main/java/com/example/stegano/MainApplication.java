package com.example.stegano;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";

    private Bitmap bitmap;

    public void setBitmap(Bitmap bitmap) {
        Log.d(TAG, "setBitmap: " + bitmap.toString());
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void clearBitmap() {
        this.bitmap = null;
    }
}
