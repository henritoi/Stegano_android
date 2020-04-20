package com.example.stegano.steganografia.image.awt;

import java.io.InputStream;

public class Font {

    public static final String TRUETYPE_FONT = null;
    public static final int BOLD = 0;
    public static final int ITALIC = 0;
    public static final int PLAIN = 0;

    public Font deriveFont(int style) {
        return this;
    }

    public Font deriveFont(float size) {
        return this;
    }

    public Font deriveFont(int style, float size) {
        return this;
    }

    public String getFontName() {
        return "dummy-android-font";
    }

    public static Font createFont(String truetypeFont, InputStream openStream) {
        return new Font();
    }

    public static Font decode(String substring) {
        return new Font();
    }

    public int getSize() {
        return 10;
    }

}
