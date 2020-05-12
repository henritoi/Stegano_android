package com.example.stegano.encoder;

import android.graphics.Bitmap;

import com.example.stegano.steganografia.crypters.CryptionType;

public interface EncoderEventListener {
    public void nextPage();
    public void previousPage();

    public void setSelectedImage(Bitmap image);
    public Bitmap getSelectedImage();

    public void setMessage(String message);
    public String getMessage();

    public void showError(String message);
}
