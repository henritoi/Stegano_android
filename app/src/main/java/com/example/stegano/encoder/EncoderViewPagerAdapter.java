package com.example.stegano.encoder;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.stegano.encoder.EncoderCryptionSelectionFragment;
import com.example.stegano.encoder.EncoderImageSelectionFragment;
import com.example.stegano.encoder.EncoderMessageFragment;

/**
 * Encder view pager adapter
 */
public class EncoderViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "EncoderViewPagerAdapter";

    /**
     * Initialization
     * @param supportFragmentManager
     */
    public EncoderViewPagerAdapter(FragmentManager supportFragmentManager) {
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
        switch (position) {
            case 0:
                return new EncoderImageSelectionFragment();
            case 1:
                return new EncoderMessageFragment();
            case 2:
                return new EncoderCryptionSelectionFragment();
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
