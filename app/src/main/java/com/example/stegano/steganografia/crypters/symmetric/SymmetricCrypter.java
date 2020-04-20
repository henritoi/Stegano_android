package com.example.stegano.steganografia.crypters.symmetric;

import com.example.stegano.steganografia.crypters.Crypter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Abstract class for Symmetric crypters
 * Extends another abstract class called Cypter.
 * @author Henri
 */
public abstract class SymmetricCrypter extends Crypter implements SymmetricCrypterInterface {
    String secret;

    /**
     * Returns crypters secret.
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets new secret to crypter.
     * @param secret the secret to set
     */
    public void setSecret(String secret) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.secret = secret;
    }

    /**
     * Checks if crypter has secret.
     * @return whether secret is set
     */
    public boolean hasSecret() {
        return this.secret != null;
    }
}