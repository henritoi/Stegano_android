package com.example.stegano.steganografia.coders;

import com.example.stegano.steganografia.image.BufferedImage;

public abstract class Coder {
    public BufferedImage image;

    /**
     * Get current image from coder
     * @return BufferedImage image
     */
    public BufferedImage getImage(){
        return this.image;
    }

    /**
     * Set current image for coder
     * @param image
     * @return
     */
    public void setImage(BufferedImage image){
        this.image = image;
    }

    /**
     * Checks whether image is set.
     * @return Boolean
     */
    public boolean hasImage() {
        return this.image != null;
    }
}