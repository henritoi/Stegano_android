package com.example.stegano.steganografia.crypters.symmetric;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.stegano.steganografia.crypters.CryptionType;
import com.example.stegano.steganografia.keys.AbstractKey;
import com.example.stegano.steganografia.keys.SymmetricKey;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Used for encrypting and decrypting messages with AES cryption method.
 * AES is symmetric cryption method which means that both encryption and decryption is done by using same key.
 * @author Henri
 */
public class AES extends SymmetricCrypter{
    private AbstractKey key;

    /**
     * Initializes class with no parameters.
     * Sets CryptionType to AES.
     */
    public AES() {
        this.cryptionType = CryptionType.AES;
    }

    /**
     * Initializes class with secret given as String.
     * New SymmetricKey is generated using given secret and used for encrypting and decrypting.
     * Sets CryptionType to AES.
     * @param secret
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public AES(String secret) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.cryptionType = CryptionType.AES;
        this.secret = secret;
        this.key = new SymmetricKey(secret);
    }

    /**
     * Setter method to set secret with new String value.
     * New SymmetricKey is generated using given secret and used for encrypting and decrypting.
     * @param secret the secret to set
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void setSecret(String secret) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.secret = secret;
        this.key = new SymmetricKey(secret);
    }

    /**
     * Encrypts given message with AES.
     * Secret must be set before using this method.
     * Message is returned as byte[].
     * @param message
     * @return byte[] encrypted message
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    @Override
    public byte[] encrypt(String message)
            throws NoSuchPaddingException, BadPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidKeyException {
        if(!hasSecret())
            throw new IllegalArgumentException("Secret must be set or given as second parameter.");
        return encrypter(message, (SymmetricKey) this.key);
    }

    /**
     * Encrypts given message with given secret that is used for constructing SymmetricKey. Message is returnes as byte[].
     * @param message
     * @param secret
     * @return byte[] encrypted message
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    @Override
    public byte[] encrypt(String message, String secret)
            throws NoSuchPaddingException, BadPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidKeyException {
        return encrypter(message, new SymmetricKey(secret));
    }

    /**
     * Helper method for encrypting given message with given symmetric key.
     * @param message
     * @param key
     * @return byte[] encrypted message
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] encrypter(String message, SymmetricKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, UnsupportedEncodingException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key.getSecretKey());
        return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8"))).getBytes();
    }

    /**
     * Decrypts given message with AES.
     * Secret must be set before using this method.
     * @param message
     * @return String decrypted message
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    @Override
    public String decrypt(String message)
            throws IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        if(!hasSecret())
            throw new IllegalArgumentException("Secret must be set or given as second parameter.");
        return decrypter(message, (SymmetricKey) this.key);
    }

    /**
     * Decrypts given message with given secret that is used for constructing SymmetricKey.
     * @param message
     * @param secret
     * @return String decrypted message
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    @Override
    public String decrypt(String message, String secret)
            throws IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        return decrypter(message, new SymmetricKey(secret));
    }

    /**
     *
     * @param message
     * @param key
     * @return String decrypted message
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private String decrypter(String message, SymmetricKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key.getSecretKey());
        return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
    }
}
