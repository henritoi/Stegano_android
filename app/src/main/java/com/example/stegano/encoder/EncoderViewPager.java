package com.example.stegano.encoder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Encoder view pager
 */
public class EncoderViewPager extends ViewPager {
    /**
     * Initialization
     * @param context
     */
    public EncoderViewPager(Context context) {
        super(context);
    }

    /**
     * Initialization
     * @param context
     * @param attrs
     */
    public EncoderViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Disable touch events
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // returning false will not propagate the swipe event
        return false;
    }

    /**
     * Disable Intercept touch events
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
