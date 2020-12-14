package com.example.stegano.onboarding;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Onboarding View Pager Adapter
 */
public class OnboardingViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "OnboardingViewPagerAdap";

    /**
     * Required contructor
     * @param supportFragmentManager
     */
    public OnboardingViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    /**
     * Initialize view pager pages
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: " + position);
        switch (position) {
            case 0:
                return new OnboardingFirstFragment();
            case 1:
                return new OnboardingSecondFragment();
            case 2:
                return new OnboardingThirdFragment();
        }
        return null;
    }

    /**
     * Initialize page count for view pager
     * @return int
     */
    @Override
    public int getCount() {
        return 3;
    }
}
