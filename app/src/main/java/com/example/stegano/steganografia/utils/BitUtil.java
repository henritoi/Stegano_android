package com.example.stegano.steganografia.utils;

public class BitUtil {

    /**
     * Converts byte[] to boolean array with offset for storing data length.
     * @param data
     * @param offset
     * @return boolean array
     */
    public static boolean[] bytesToBits(byte[] data, int offset) {
        // offset bits for data length + data as bits
        boolean[] bits = new boolean[offset + data.length * 8];
        // Data length to bits
        String dataLength = Integer.toBinaryString(data.length);
        // Add zeros to front
        while(dataLength.length() < offset) {
            dataLength = "0" + dataLength;
        }
        // Data length to array
        for(int i = 0; i < offset; i++) {
            // If current bit is 1 it's set as true
            bits[i] = dataLength.charAt(i) == '1';
        }
        // Data to array byte at a time.
        byte b;
        for(int i = 0; i < data.length; i++) {
            b = data[i];
            // Byte = 8 bits
            for(int j = 0; j < 8; j++) {
                bits[offset + (i * 8) + j] = ((b >> (7 - j)) & 1) == 1;
            }
        }
        return bits;
    }

    /**
     * Coverts boolean[] to integer
     * @param bool
     * @return int
     */
    public static int booleanArrayToInteger(boolean[] bool) {
        int ret = 0;
        for(int i = 0; i < bool.length; i++){
            if(bool[i]){
                ret |= (1 << (31 - i));
            }
        }
        return ret;
    }

    /**
     * Converts given boolean[] to byte[].
     * @param bool
     * @return byte[]
     */
    public static byte[] booleanArrayToBytes(boolean[] bool) {
        byte[] data = new byte[bool.length / 8];
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < 8; j++){
                if(bool[i * 8 + j]){
                    data[i] |= (1 << (7 - j));
                }
            }
        }
        return data;
    }
}
