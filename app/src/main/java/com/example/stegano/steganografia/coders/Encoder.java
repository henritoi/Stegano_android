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

    /**
     * TODO: Implement save to Android
     * Saves classes BufferedImage to given file destination. Destination is given as File.
     * If image is not set to class method throws IllegalArgumentException.
     * If image is type that is not supported method throws ImageTypeNotSupported exception.
     * @param destination
     * @throws IOException
     * @throws ImageTypeNotSupported
     */

    /*public void save(File destination) throws IOException {
        if(!hasImage()) {
            throw new IllegalArgumentException("Image is not set");
        }
        saveImage(this.image, destination);
    }
    */

    /**
     * TODO: Implement save to Android
     * Saves given BufferedImage to given file destination. Destination is given as File.
     * If image is type that is not supported method throws ImageTypeNotSupported exception.
     * @param image
     * @param destination
     * @throws IOException
     * @throws ImageTypeNotSupported
     */
    /*
    public void save(BufferedImage image, File destination) throws IOException {
        saveImage(image, destination);
    }
     */

    /**
     * TODO: Implement save to Android
     * Help method to save given BufferedImage to given file destination. Destination is given as File.
     * If image is type that is not supported method throws ImageTypeNotSupported exception.
     * @param image
     * @param destination
     * @throws IOException
     * @throws ImageTypeNotSupported
     */
    /*
    private void saveImage(BufferedImage image, File destination) throws IOException {
        String extension = "";
        int i = destination.getName().lastIndexOf('.');
        if(i > 0) extension = destination.getName().substring(i + 1).toUpperCase();

        if(SupportedImageTypes.contains(extension)){
            // Jpg works when it's saved as png
            ImageIO.write(image, "png", destination);

        }else {
            throw new ImageTypeNotSupported("Image type " + extension + "is not supported. Tuetut kuvatyypit ovat " + SupportedImageTypes.getAsString() + ".");
        }
    }
     */
}
