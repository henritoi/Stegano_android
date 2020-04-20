package com.example.stegano.steganografia.image.awt;

public class Rectangle2D implements Shape {

    private Rectangle rect = new Rectangle();

    public Rectangle2D() {
    }

    public Rectangle2D(int x, int y, int width, int height) {
        rect.setBounds(x, y, width, height);
    }

    public Rectangle getBounds() {
        return rect;
    }

    public float getHeight() {
        return rect.height;
    }

    public float getWidth() {
        return rect.width;
    }

    public float getX() {
        return rect.x;
    }

    public void setHeight(int height) {
        rect.height = height;
    }

    public void setWidth(int width) {
        rect.width = width;
    }
}
