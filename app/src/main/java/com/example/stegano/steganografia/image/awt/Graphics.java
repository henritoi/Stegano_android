package com.example.stegano.steganografia.image.awt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.example.stegano.steganografia.image.BufferedImage;

public abstract class Graphics {

    protected Canvas canvas = new Canvas();

    protected android.graphics.Paint paint = new Paint();

    protected Color color;

    protected Rectangle clipBounds;

    public Graphics2D create() {
        // TODO Auto-generated method stub
        System.out.println("ERROR!");new Exception().printStackTrace();throw new UnsupportedOperationException("Broken!");
    }

    public void dispose() {
        // Does nothing
    }

    public void drawImage(Image image, int x, int y, Object object) {
        Bitmap src = image.getBitmap();
        canvas.drawBitmap(src, x, y, null);
    }

    public void drawImage(BufferedImage image, int x, int y, int width,
                          int height, Object object) {
        Bitmap src = image.getBitmap();
        RectF dest = new RectF(x, y, x + width, y + height);
        canvas.drawBitmap(src, null, dest, paint);
    }

    public void drawLine(int x, int y, int x2, int y2) {
        canvas.drawLine(x, y, x2, y2, paint);
    }

    public void drawOval(int x, int y, int width, int height) {
        canvas.drawOval(new RectF(x, y, x + width, y + height), paint);
    }

    public void drawRect(int x, int y, int width, int height) {
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public void drawString(String message, int x, int y) {
        canvas.drawText(message, x, y, paint);
    }

    public void fillOval(int x, int y, int width, int height) {
        paint.setStyle(Style.FILL);
        canvas.drawOval(new RectF(x, y, x + width, y + height), paint);
        paint.setStyle(Style.STROKE);
    }

    public void fillRect(int x, int y, int width, int height) {
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width, y + height, paint);
        paint.setStyle(Style.STROKE);
    }

    public Rectangle getClipBounds() {
        return clipBounds;
    }

    public Color getColor() {
        return color;
    }

    public Font getFont() {
        return new Font();
    }

    public FontMetrics getFontMetrics() {
        return new FontMetrics(paint);
    }

    public FontMetrics getFontMetrics(Font font) {
        return new FontMetrics(paint);
    }

    public void setColor(Color color) {
        if (color == null) {
            setColor(Color.BLACK);
        } else {
            this.color = color;
            paint.setColor(color.getRGB());
        }
    }

    public void setFont(Font nameFont) {
        // Does nothing
    }

    public void translate(int i, int j) {
        canvas.translate(i, j);
    }
}