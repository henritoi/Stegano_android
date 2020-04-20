package com.example.stegano.steganografia.crypters.other;

import com.example.stegano.steganografia.crypters.Crypter;
import com.example.stegano.steganografia.crypters.CryptionType;

/**
 * Implementation for Caesar cipher.
 * Caesar cipher is a shift cipher.
 * @author Henri
 */
public class CaesarCipher extends Crypter {

    private int shiftKey = 1;
    // Custom alphabet
    private static final String ALBHABET = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZzÅåÄäÖö";

    /**
     * Initializes class as empty.
     * Sets CryptionMode to CAESAR.
     */
    public CaesarCipher() {
        this.cryptionType = CryptionType.CAESAR;
    }

    /**
     * Initializes class with shift key to be used for encryption and decryption.
     * @param shiftKey
     */
    public CaesarCipher(int shiftKey) {
        this.cryptionType = CryptionType.CAESAR;
        this.shiftKey = shiftKey;
    }

    /**
     * Returns used alphabet as string.
     * @return String alphabet
     */
    public String getAlphabet() {
        return ALBHABET;
    }

    /**
     * Encrypts given message with shift value that is set earlier (default: 1).
     * @param message
     * @return String encrypted message
     */
    public String encrypt(String message) {
        return encrypter(message, this.shiftKey);
    }

    /**
     * Encrypts given message with given shift value (not with shift key set in class).
     * @param message
     * @param shiftKey
     * @return String encrypted message
     */
    public String encrypt(String message, int shiftKey) {
        return encrypter(message, shiftKey);
    }

    /**
     * Help method for encrypting given message with given shift value.
     * @param message
     * @param shiftKey
     * @return String encrypted message
     */
    private String encrypter(String message, int shiftKey) {
        String encryptedMessage = "";
        int charPosition, keyValue;
        for(int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            if(charInAlphabet(currentChar)) {
                charPosition = getAlphabet().indexOf(currentChar);
                keyValue = (charPosition + shiftKey * 2 ) % (getAlphabet().length());
                encryptedMessage += getAlphabet().charAt(keyValue);
            }else {
                encryptedMessage += currentChar;
            }
        }
        return encryptedMessage;
    }

    /**
     * Decrypts given message with shift value that is set earlier (default: 1).
     * @param encryptedMessage
     * @return String decrypted message
     */
    public String decrypt(String encryptedMessage) {
        return decrypter (encryptedMessage, this.shiftKey);
    }

    /**
     * Decrypts given message with given shift value (not with shift key set in class).
     * @param encryptedMessage
     * @param shiftKey
     * @return String decrypted message
     */
    public String decrypt(String encryptedMessage, int shiftKey) {
        return decrypter(encryptedMessage, shiftKey);
    }

    /**
     * Help method for decrypting given message with given shift value.
     * @param encryptedMessage
     * @param shiftKey
     * @return String decrypted message
     */
    private String decrypter(String encryptedMessage, int shiftKey) {
        String decryptedMessage = "";
        int charPosition, keyValue;
        for(int i = 0; i < encryptedMessage.length(); i++) {
            char currentChar = encryptedMessage.charAt(i);
            if(charInAlphabet(currentChar)) {
                charPosition = getAlphabet().indexOf(encryptedMessage.charAt(i));
                keyValue = (charPosition - shiftKey * 2 ) % (getAlphabet().length());
                if(keyValue < 0) keyValue = getAlphabet().length() + keyValue;
                decryptedMessage += getAlphabet().charAt(keyValue);
            }else {
                decryptedMessage += currentChar;
            }
        }
        return decryptedMessage;
    }

    /**
     * Getter for classes shift value.
     * @return the key value
     */
    public int getKey() {
        return this.shiftKey;
    }

    /**
     * Setter for classes shift value
     * @param shiftKey the key to set
     */
    public void setKey(int shiftKey) {
        this.shiftKey = shiftKey;
    }

    /**
     * Help method for checking whether given character is in the alphabet.
     * @param ch
     * @return Boolean charInAlphabet
     */
    private boolean charInAlphabet(char ch) {
        for(int i = 0; i < getAlphabet().length(); i++) {
            if(ch == getAlphabet().charAt(i)) return true;
        }
        return false;
    }
}