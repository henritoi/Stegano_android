package com.example.stegano.steganografia.crypters;

/**
 * Enum for supported cryption types.
 * @author Henri
 */
public enum CryptionType {
    NONE,
    AES,
    //RSA,
    CAESAR;

    /**
     * Returns boolean whether given cryption type as String is in the supported cryption types.
     * @param type
     * @return boolean
     */
    public static boolean contains(String type) {
        for(CryptionType c : CryptionType.values()) {
            if(c.name().equals(type.toUpperCase())) return true;
        }
        return false;
    }
}
