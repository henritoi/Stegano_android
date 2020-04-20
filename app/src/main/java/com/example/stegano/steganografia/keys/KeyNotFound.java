package com.example.stegano.steganografia.keys;

/**
 * RuntimeException for cause that key is not found.
 * @author Henri
 */
public class KeyNotFound extends RuntimeException{
    public KeyNotFound() { super(); }
    public KeyNotFound(String message) { super(message); }
    public KeyNotFound(String message, Throwable cause) {super(message, cause);}
    public KeyNotFound(Throwable cause) {super(cause);}
}
