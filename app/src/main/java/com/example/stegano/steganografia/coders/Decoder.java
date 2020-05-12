package com.example.stegano.steganografia.coders;

import com.example.stegano.steganografia.image.BufferedImage;
import com.example.stegano.steganografia.utils.BitUtil;

/**
 * Class used for decoding messages as byte[] from given BufferedImages that has messages hidden in them.
 * @author Henri
 */
public class Decoder extends Coder{

    /**
     * Initializes class as empty
     */
    public Decoder() {}

    /**
     * Initializes class with BufferedImage
     * @param image
     */
    public Decoder(BufferedImage image) {
        this.image = image;
    }

    /**
     * Decodes message from classes image. If image is not set method throws IllegamArgumentException.
     * Returns message as byte[].
     * @return byte[] message
     * @throws IllegalArgumentException
     */
    public byte[] decode() {
        if(this.image == null)
            throw new IllegalArgumentException("Image is not set.");
        return decoder(this.image);
    }

    public int decodeMessageLength() {
        if(this.image == null)
            throw new IllegalArgumentException("Image is not set.");
        return decodeLength(image);
    }

    public int decodeMessageLength(BufferedImage image) {
        return decodeLength(image);
    }

    /**
     * Decodes message from given image.
     * Returns message as byte[].
     * @return byte[] message
     */
    public byte[] decode(BufferedImage image) {
        return decoder(image);
    }

    /**
     * TODO: Implement decode for android bitmap
     * Help method to decode byte[] message from given BufferedImage.
     * @param image
     * @return byte[] message
     */
    private byte[] decoder(BufferedImage image) {
        int x = 0, y = 0, w = image.getWidth(), h = image.getHeight();

        boolean[] dataLength = new boolean[32];
        for(int i = 0; i < 32; i++) {
            if(x < w){
                String binary = Integer.toBinaryString(image.getRGB(x, y));
                //Jos viimeinen bitti on 1 lisää true
                dataLength[i] = binary.charAt(binary.length() - 1) == '1';
                x++;
            }else {
                x = 0;
                y++;
                String binary = Integer.toBinaryString(image.getRGB(x, y));
                dataLength[i] = binary.charAt(binary.length() - 1) == '1';
            }
        }

        int length = BitUtil.booleanArrayToInteger(dataLength);
        //System.out.println(length);
        // Read data from image as bits
        boolean[] dataBits = new boolean[length * 8];
        for(int i = 0; i < length * 8; i++){
            if(x < image.getWidth()){
                String binary = Integer.toBinaryString(image.getRGB(x, y));
                //Jos viimeinen bitti on 1 lisää true
                dataBits[i] = binary.charAt(binary.length() - 1) == '1';
                x++;
            }else {
                x = 0;
                y++;
                String binary = Integer.toBinaryString(image.getRGB(x, y));
                dataBits[i] = binary.charAt(binary.length() - 1) == '1';
            }
        }
        // Now convert bits to bytes
        byte[] data = BitUtil.booleanArrayToBytes(dataBits);
        return data;
    }

    private int decodeLength(BufferedImage image) {
        int x = 0, y = 0, w = image.getWidth(), h = image.getHeight();

        boolean[] dataLength = new boolean[32];
        for(int i = 0; i < 32; i++) {
            if(x < w){
                String binary = Integer.toBinaryString(image.getRGB(x, y));
                //Jos viimeinen bitti on 1 lisää true
                dataLength[i] = binary.charAt(binary.length() - 1) == '1';
                x++;
            }else {
                x = 0;
                y++;
                String binary = Integer.toBinaryString(image.getRGB(x, y));
                dataLength[i] = binary.charAt(binary.length() - 1) == '1';
            }
        }

        int length = BitUtil.booleanArrayToInteger(dataLength);
        return length < w * h ? length : -1;
    }
}
