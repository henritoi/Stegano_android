package com.example.stegano.decoder;

import android.graphics.Bitmap;

import com.example.stegano.steganografia.crypters.CryptionType;

public interface DecoderEventListener {
    public void setSelectedImage(Bitmap image);
    public Bitmap getSelectedImage();

    public void showError(String message);

    public void noMessageFound();

    public boolean hasSendImage();
}
