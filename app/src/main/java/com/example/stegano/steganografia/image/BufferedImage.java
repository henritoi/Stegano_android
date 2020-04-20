package com.example.stegano.steganografia.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.example.stegano.steganografia.image.awt.Graphics2D;
import com.example.stegano.steganografia.image.awt.Image;

public class BufferedImage  extends Image {
    public static final String TYPE_INT_ARGB = null;

    private Bitmap bitmap;

    public BufferedImage(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Config.ARGB_8888, true);
    }

    public BufferedImage(int width, int height, String typeIntArgb) {
        bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    /*public Graphics2D getGraphics() {
        return new Graphics2D(this);
    }

    public Graphics2D createGraphics() {
        return new Graphics2D(this);
    }
    */

    public BufferedImage getSubimage(int x, int y, int width, int height) {
        return new BufferedImage(Bitmap.createBitmap(bitmap, x, y, width, height, null, false));
    }

    public int getRGB(int biX, int biY) {
        return bitmap.getPixel(biX, biY);
    }

    public void setRGB(int biX, int biY, int color) {
        bitmap.setPixel(biX, biY, color);
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }
}
