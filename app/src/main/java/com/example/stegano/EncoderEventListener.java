package com.example.stegano;

import android.graphics.Bitmap;

public interface EncoderEventListener {
    public void nextPage();
    public void previousPage();

    public void setSelectedImage(Bitmap image);
    public void setMessage(String message);
}
