package com.example.stegano.steganografia.keys;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

/**
 * Key used for symmetric cryption.
 * @author Henri
 */
public class SymmetricKey extends AbstractKey{
    private SecretKeySpec secretKey;
    private byte[] key;
    private String secret;

    /**
     * Generated new Symmetric key using given secret given as String.
     * @param secret
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public SymmetricKey(String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.secret = secret;

        MessageDigest sha = null;

        sha = MessageDigest.getInstance("SHA-1");
        this.key = this.secret.getBytes("UTF-8");
        this.key = sha.digest(this.key);
        this.key = Arrays.copyOf(this.key, 16);
        this.secretKey = new SecretKeySpec(this.key, "AES");
    }

    /**
     * Returns Key as SecretKeySpec.
     * @return SecretKeySpec secret key
     */
    public SecretKeySpec getSecretKey() {
        return this.secretKey;
    }
}