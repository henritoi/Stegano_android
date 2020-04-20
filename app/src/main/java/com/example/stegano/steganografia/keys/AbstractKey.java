package com.example.stegano.steganografia.keys;

/**
 * Abstact class for Key.
 * Has private String secret and get method for it.
 * @author Henri
 */
public abstract class AbstractKey {
    private String secret;

    private String getSecret() {
        return this.secret;
    }
}
