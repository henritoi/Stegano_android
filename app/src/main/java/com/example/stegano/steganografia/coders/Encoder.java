package com.example.stegano.steganografia.coders;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.stegano.steganografia.image.BufferedImage;
import com.example.stegano.steganografia.utils.BitUtil;

public class Encoder extends Coder {
    private static final String TAG = "Encoder";

    /**
     * Initializes class as empty.
     */
    public Encoder() {}

    /**
     * Initializes class with BufferedImage.
     * @param image
     */
    public Encoder(BufferedImage image) {
        this.image = image;
    }

    /**
     * Encodes given byte[] to BufferedImage which is already set to the class.
     * If image is not set method throws IllegalArgumentException.
     * @param data
     * @return BufferedImage image
     */
    public BufferedImage encode(byte[] data) {
        if(!hasImage()) throw new IllegalArgumentException("Image is not set");
        return encoder(this.image, data);
    }

    /**
     * Encodes given byte[] to given BufferedImage.
     * @param image
     * @param data
     * @return BufferedImage image
     */
    public BufferedImage encode(BufferedImage image, byte[] data) {
        return encoder(image, data);
    }

    /**
     * TODO: Implement encoding for android bitmap
     * Help method to encode given byte[] to given BufferedImage
     * @param image
     * @param data
     * @return BufferedImage image
     */
    private BufferedImage encoder(BufferedImage image, byte[] data) {
        int x = 0, y = 0, w = image.getWidth(), h = image.getHeight();
        // Convertion in BitUtil to simplify this class
        boolean[] bits = BitUtil.bytesToBits(data, 32);

        // Encode bits to image
        for(boolean b : bits) {
            // Bit is 1
            if(b) {
                if(x < w) {
                    image.setRGB(x, y, (image.getRGB(x, y) | 0x00000001));
                    x++;
                }else {
                    x = 0;
                    y++;
                    image.setRGB(x, y, (image.getRGB(x, y) | 0x00000001));
                }
            }else {
                // Bit is 0
                if(x < w) {
                    image.setRGB(x, y, (image.getRGB(x, y) & 0xFFFFFFFE));
                    x++;
                }else {
                    x = 0;
                    y++;
                    image.setRGB(x, y, (image.getRGB(x, y) & 0xFFFFFFFE));
                }
            }
        }
        return image;
    }
}
