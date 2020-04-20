package com.example.stegano.steganografia.crypters.symmetric;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Interfase for Symmetric cryption.
 * @author Henri
 */
interface SymmetricCrypterInterface {
    byte[] encrypt(String message)
            throws NoSuchPaddingException, BadPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidKeyException;

    byte[] encrypt(String message, String secret)
            throws NoSuchPaddingException, BadPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidKeyException;

    String decrypt(String message)
            throws IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException;

    String decrypt(String message, String secret)
            throws IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException;
}