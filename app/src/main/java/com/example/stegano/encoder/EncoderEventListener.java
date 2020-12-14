package com.example.stegano.encoder;

import android.graphics.Bitmap;

import com.example.stegano.steganografia.crypters.CryptionType;

/**
 * Event Listener for Encoder
 */
public interface EncoderEventListener {
    /**
     * Go to next page
     */
    public void nextPage();

    /**
     * Go to previous page
     */
    public void previousPage();

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
     * Setter for message
     * @param message
     */
    public void setMessage(String message);

    /**
     * Getter for message
     * @return
     */
    public String getMessage();

    /**
     * Show given error in error dialog
     * @param message
     */
    public void showError(String message);

    /**
     * Check if has sendImage
     * @return
     */
    public boolean hasSendImage();
}
