package com.example.stegano.steganografia.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class BufferedImage  extends Image {
    public static final String TYPE_INT_ARGB = null;

    private Bitmap bitmap;

    /**
     * Contructor for custom buffered image class
     * @param bitmap
     */
    public BufferedImage(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Config.ARGB_8888, true);
    }

    /**
     * Constructor for custom buffered image class
     * @param width
     * @param height
     * @param typeIntArgb
     */
    public BufferedImage(int width, int height, String typeIntArgb) {
        bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
    }

    /**
     * Helper method to get bitmap
     * @return
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Helper method ot get subimage which is not used in the application
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public BufferedImage getSubimage(int x, int y, int width, int height) {
        return new BufferedImage(Bitmap.createBitmap(bitmap, x, y, width, height, null, false));
    }

    /**
     * Returns a single pixel in the image position x, y
     * @param biX
     * @param biY
     * @return int color
     */
    public int getRGB(int biX, int biY) {
        return bitmap.getPixel(biX, biY);
    }

    /**
     * Set a single pixel (x, y) with given color values
     * @param biX
     * @param biY
     * @param color
     */
    public void setRGB(int biX, int biY, int color) {
        bitmap.setPixel(biX, biY, color);
    }

    /**
     * Get image width
     * @return int width
     */
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    /**
     * Get image height
     * @return int height
     */
    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }
}
