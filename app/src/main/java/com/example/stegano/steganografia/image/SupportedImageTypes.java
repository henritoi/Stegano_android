package com.example.stegano.steganografia.image;

/**
 * Enum for supported image types.
 * @author Henri
 */
public enum SupportedImageTypes {
    PNG,
    JPG;

    /**
     * Returns boolean whether given image type given as String is in the supported image types.
     * @param type
     * @return boolean
     */
    public static boolean contains(String type){
        for(SupportedImageTypes s : SupportedImageTypes.values()){
            if(s.name().equals(type.toUpperCase())) return true;
        }
        return false;
    }

    /**
     * Returns supported image types as string.
     * @return String supported image types
     */
    public static String getAsString() {
        String tmp = "";
        for(int i = 0; i < SupportedImageTypes.values().length; i++) {
            SupportedImageTypes s = SupportedImageTypes.values()[i];
            if(i == SupportedImageTypes.values().length - 1) {
                tmp += s.name();
            }else {
                tmp += s.name() + ", ";
            }
        }
        return tmp;
    }
}
