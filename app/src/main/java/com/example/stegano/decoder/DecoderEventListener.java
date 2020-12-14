package com.example.stegano.decoder;

import android.graphics.Bitmap;

import com.example.stegano.steganografia.crypters.CryptionType;

/**
 * Decoder Event Listener
 */
public interface DecoderEventListener {
    /**
     * Setter for selected image
     * @param image
     */
    public void setSelectedImage(Bitmap image);

    /**
     * Getter for selected image
     * @return
     */
    public Bitmap getSelectedImage();

    /**
     * Show error message
     * @param message
     */
    public void showError(String message);

    /**
     * No message found handle
     */
    public void noMessageFound();

    /**
     * Check if has SendImage
     * @return
     */
    public boolean hasSendImage();
}
