package com.example.stegano.steganografia.image.awt;

import java.util.ArrayList;
import java.util.List;

public class GeneralPath implements Shape {

    public static final int WIND_EVEN_ODD = 1;

    private int rule;

    private List<Segment> segments = new ArrayList<Segment>();

    public GeneralPath(int rule) {
        this.rule = rule;
    }

    public GeneralPath() {
    }

    public GeneralPath(Shape shape) {
        // TODO Auto-generated constructor stub
        System.out.println("ERROR!");new Exception().printStackTrace();throw new UnsupportedOperationException("Broken!");
    }


    public void closePath() {
        segments.add(new Close());
    }

    public void moveTo(double x, double y) {
        segments.add(new MoveTo((float)x, (float)y));
    }

    public void lineTo(double x, double y) {
        segments.add(new LineTo((float)x, (float)y));
    }

    public void append(Shape shape, boolean connect) {
        // TODO Auto-generated method stub
        System.out.println("ERROR!");new Exception().printStackTrace();throw new UnsupportedOperationException("Broken!");
    }

    public void quadTo(double x1, double y1, double x2, double y2) {
        segments.add(new QuadTo((float)x1, (float)y1, (float)x2, (float)y2));
    }

    public void reset() {
        segments.clear();
    }

    public void transform(AffineTransform transform) {
        segments.add(new Transform(transform));
    }

    private interface Segment {

    }

    private static class MoveTo implements Segment {

        private float x;

        private float y;

        public MoveTo(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }

    private static class LineTo implements Segment {

        private float x;

        private float y;

        public LineTo(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }

    private static class QuadTo implements Segment {

        private float x1;

        private float y1;

        private float x2;

        private float y2;

        public QuadTo(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

    }

    private static class Transform implements Segment {

        private AffineTransform transform;

        public Transform(AffineTransform transform) {
            this.transform = transform;
        }

    }

    private static class Close implements Segment {

    }
}
