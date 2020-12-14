package com.example.stegano.onboarding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Onboarding viewpager
 */
public class OnboardingViewPager extends ViewPager {
    /**
     * Contructor
     * @param context
     */
    public OnboardingViewPager(Context context) {
        super(context);
    }
    /**
     * Contructor
     * @param context
     */
    public OnboardingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
