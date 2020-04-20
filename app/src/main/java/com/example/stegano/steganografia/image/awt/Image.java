package com.example.stegano.steganografia.image.awt;

import android.graphics.Bitmap;

public abstract class Image {

    public static final String SCALE_REPLICATE = null;
    public static final String SCALE_SMOOTH = null;
    public static final String SCALE_DEFAULT = null;

    public abstract Bitmap getBitmap();

    public int getWidth() {
        return getBitmap().getWidth();
    }

    public int getHeight() {
        return getBitmap().getHeight();
    }

    public int getWidth(Object object) {
        return getWidth();
    }

    public Image getScaledInstance(int width, int height, String scaleReplicate) {
        return this;
    }

    public int getHeight(Object object) {
        return getHeight();
    }

}
