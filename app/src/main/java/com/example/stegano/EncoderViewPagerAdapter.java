package com.example.stegano;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EncoderViewPagerAdapter extends FragmentPagerAdapter {

    public EncoderViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

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

    @Override
    public int getCount() {
        return 3;
    }
}
