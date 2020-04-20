package com.example.stegano.steganografia.image.awt;

import android.graphics.Matrix;

public class AffineTransform {

    private final Matrix matrix;

    public AffineTransform(Matrix matrix) {
        this.matrix = matrix;
    }

    public static AffineTransform getScaleInstance(double d, double e) {
        // TODO Auto-generated method stub
        System.out.println("ERROR!");new Exception().printStackTrace();throw new UnsupportedOperationException("Broken!");
    }

    public static AffineTransform getTranslateInstance(double d, double e) {
        // TODO Auto-generated method stub
        System.out.println("ERROR!");new Exception().printStackTrace();throw new UnsupportedOperationException("Broken!");
    }

    public Matrix getMatrix() {
        return matrix;
    }

}