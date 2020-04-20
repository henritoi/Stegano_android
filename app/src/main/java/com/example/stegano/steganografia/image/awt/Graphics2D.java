package com.example.stegano.steganografia.image.awt;

import com.example.stegano.steganografia.image.BufferedImage;

import android.graphics.Canvas;

public class Graphics2D extends Graphics{

    public Graphics2D(BufferedImage image) {
        canvas.setBitmap(image.getBitmap());
        clipBounds = new Rectangle(0, 0, image.getWidth(), image.getHeight());
    }

    public Graphics2D(Canvas canvas) {
        this.canvas = canvas;
        clipBounds = new Rectangle(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public Graphics2D() {
        clipBounds = new Rectangle();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        clipBounds.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void draw(GeneralPath path) {
        //FIXME Implement
    }

    public void drawImage(BufferedImage bi, RescaleOp rop, int x, int y) {
        drawImage(bi, x, y, null);
    }

    public void fill(GeneralPath cross) {
        //FIXME Implement
    }

    public Composite getComposite() {
        return new Composite();
    }

    public Object getFontRenderContext() {
        return new Object();
    }

    public Stroke getStroke() {
        return new Stroke() {
        };
    }

    public AffineTransform getTransform() {
        return new AffineTransform(canvas.getMatrix());
    }

    public void scale(float sx, float sy) {
        canvas.scale(sx, sy);
    }

    public void setComposite(Composite instance) {
        //FIXME Implement!
    }

    public void setPaint(Paint paint) {
        // TODO Auto-generated method stub
        System.out.println("ERROR!");
        new Exception().printStackTrace();
        throw new UnsupportedOperationException("Broken!");
    }

    public void setRenderingHint(String keyTextAntialiasing, Object textAA) {
        // Does nothing
    }

    public void setStroke(Stroke basicStroke) {
        // Does nothing
    }

    public void setTransform(AffineTransform transform) {
        canvas.setMatrix(transform.getMatrix());
    }
}
