package com.example.stegano.steganografia.image;

/**
 * Runtime exception that is thrown when image type that is not supported by
 * this library is given.
 * @author Henri
 */
public class ImageTypeNotSupported extends RuntimeException{
    public ImageTypeNotSupported() { super(); }
    public ImageTypeNotSupported(String message) { super(message); }
    public ImageTypeNotSupported(String message, Throwable cause) {super(message, cause);}
    public ImageTypeNotSupported(Throwable cause) {super(cause);}
}
