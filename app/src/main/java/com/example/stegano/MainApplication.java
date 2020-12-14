package com.example.stegano;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * "Global memory"
 */
public class MainApplication extends Application {
    private static final String TAG = "MainApplication";

    private Bitmap bitmap;
    private String message;

    private Bitmap sendBitmap;

    /**
     * Set bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Get bitmap that is set
     * @return Bitmap bitmap
     */
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    /**
     * Clear (set null) bitmap
     */
    public void clearBitmap() {
        this.bitmap = null;
    }

    /**
     * Set message to value
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get message that is set
     * @return String message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Clear (set null) message
     */
    public void clearMessage() {
        this.message = null;
    }

    /**
     * Clear (set null) both bitmap and message
     */
    public void clear() {
        clearBitmap();
        clearMessage();
        clearSendBitmap();
    }

    public void clearSendBitmap() {
        this.sendBitmap = null;
    }

    /**
     * Set bitmap
     */
    public void setSendBitmap(Bitmap bitmap) {
        this.sendBitmap = bitmap;
    }

    /**
     * Get bitmap that is set
     * @return Bitmap bitmap
     */
    public Bitmap getSendBitmap() {
        return this.sendBitmap;
    }
}
