package com.example.stegano.steganografia.crypters;

/**
 * Abstract class for Cypters.
 * @author Henri
 */
public abstract class Crypter {
    public CryptionType cryptionType;

    /**
     * @return the cryptionType
     */
    public CryptionType getCryptionType() {
        return cryptionType;
    }

    /**
     * @param cryptionType the cryptionType to set
     */
    public void setCryptionType(CryptionType cryptionType) {
        this.cryptionType = cryptionType;
    }
}
