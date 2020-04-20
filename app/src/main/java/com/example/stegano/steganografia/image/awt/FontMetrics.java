package com.example.stegano.steganografia.image.awt;

import android.graphics.Paint;

public class FontMetrics {

    private Paint paint;

    public FontMetrics(Paint paint) {
        this.paint = paint;
    }

    public Rectangle2D getStringBounds(String text, Graphics g) {
        Rectangle2D bounds = new Rectangle2D();
        if (text != null) {
            bounds.setHeight(20);
            if (paint != null) {
                bounds.setWidth(stringWidth(text));
            }
        }
        return bounds;
    }

    public int stringWidth(String text) {
        int height = 0;
        if (text != null) {
            if (paint != null) {
                return (int) paint.measureText(text);
            } else {
                return 15 * text.length();
            }
        }
        return height;
    }

    public int getMaxAscent() {
        if (paint != null) {
            return Math.abs(paint.getFontMetricsInt().top);
        } else {
            return 10;
        }
    }

    public int getMaxDescent() {
        if (paint != null) {
            return Math.abs(paint.getFontMetricsInt().bottom);
        } else {
            return 10;
        }
    }

    public int getAscent() {
        if (paint != null) {
            return Math.abs(paint.getFontMetricsInt().ascent);
        } else {
            return 0;
        }
    }

    public int getDescent() {
        if (paint != null) {
            return Math.abs(paint.getFontMetricsInt().descent);
        } else {
            return 0;
        }
    }

}