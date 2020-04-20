package com.example.stegano.steganografia.image.awt;

import android.graphics.Point;
import android.graphics.Rect;

public class Rectangle {

    public int x;

    public int y;

    public int width;

    public int height;

    private Rect helper = new Rect();

    public Rectangle() {
    }

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rect rect) {
        this.x = rect.left;
        this.y = rect.top;
        this.width = rect.right - rect.left;
        this.height = rect.bottom - rect.top;
    }

    public void add(int x, int y) {
        // Extends Left
        if (x < this.x) {
            width += Math.abs(this.x - x);
            this.x = x;
        }
        // Extend Up
        if (y < this.y) {
            height += Math.abs(this.y - y);
            this.y = y;
        }
        // Extend Right
        if (x >= (this.x + width)) {
            width += Math.abs(x - (x + width - 1));
        }
        // Extend Down
        if (y >= (this.y + height)) {
            height += Math.abs(y - (y + height - 1));
        }
    }

    public boolean contains(int x, int y) {
        updateHelper();
        return helper.contains(x, y);
    }

    public boolean contains(Point point) {
        return contains(point.x, point.y);
    }

    public boolean intersects(Rectangle clipArea) {
        updateHelper();
        clipArea.updateHelper();
        return helper.intersect(clipArea.helper);
    }

    public boolean isEmpty() {
        updateHelper();
        return helper.isEmpty();
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle union(Rectangle r2) {
        updateHelper();
        r2.updateHelper();
        helper.union(r2.helper);
        return new Rectangle(helper);
    }

    private void updateHelper() {
        helper.set(x, y, x + width, y + height);
    }
}