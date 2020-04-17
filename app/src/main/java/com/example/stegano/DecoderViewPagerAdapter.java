package com.example.stegano;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DecoderViewPagerAdapter extends FragmentPagerAdapter {

    public DecoderViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DecoderImageSelectionFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
