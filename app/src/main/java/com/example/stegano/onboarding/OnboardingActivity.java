package com.example.stegano.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.stegano.R;
import com.example.stegano.encoder.EncoderViewPager;
import com.example.stegano.encoder.EncoderViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class OnboardingActivity extends AppCompatActivity {
    private static final String TAG = "OnboardingActivity";

    private OnboardingViewPager onboardingViewPager;
    private TabLayout onboardingTabDots;
    private OnboardingViewPagerAdapter onboardingViewPagerAdapter;

    private Button skipButton, nextButton, continueButton;
    private LinearLayout onboardingDefaultButtonsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_onboarding);

        onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPagerAdapter = new OnboardingViewPagerAdapter(getSupportFragmentManager());
        onboardingViewPager.setAdapter(onboardingViewPagerAdapter);
        onboardingViewPager.setOffscreenPageLimit(onboardingViewPagerAdapter.getCount() - 1); // Default 1
        onboardingViewPager.addOnPageChangeListener(pageChanged);

        onboardingTabDots = findViewById(R.id.onboardingTabDots);
        onboardingTabDots.setupWithViewPager(onboardingViewPager, true);

        skipButton = (Button) findViewById(R.id.onboardingSkipButton);
        nextButton = (Button) findViewById(R.id.onboardingNextButton);
        continueButton = (Button) findViewById(R.id.onboardingContinueButton);

        skipButton.setOnClickListener(handleClick);
        nextButton.setOnClickListener(handleClick);
        continueButton.setOnClickListener(handleClick);

        onboardingDefaultButtonsContainer = findViewById(R.id.onboardingDefaultButtonsContainer);
    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.onboardingSkipButton:
                    Log.d(TAG, "onClick: Skip");
                    finish();
                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                    return;
                case R.id.onboardingNextButton:
                    Log.d(TAG, "onClick: Next");
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                    return;
                case R.id.onboardingContinueButton:
                    Log.d(TAG, "onClick: Continue");
                    finish();
                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                    return;
                default:
                    return;
            }
        }
    };

    private ViewPager.OnPageChangeListener pageChanged = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            handlePageChange(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            
        }
    };

    private void handlePageChange(int position) {
        // Last page
        if(position == this.onboardingViewPagerAdapter.getCount() - 1) {
            Animation animationOut =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
            this.onboardingDefaultButtonsContainer.startAnimation(animationOut);

            this.onboardingDefaultButtonsContainer.setVisibility(View.GONE);

            this.continueButton.setVisibility(View.VISIBLE);
            this.continueButton.setClickable(true);
            Animation animationIn =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
            this.continueButton.startAnimation(animationIn);
        }else {
            if(this.onboardingDefaultButtonsContainer.getVisibility() == View.GONE) {
                Animation animationOut =
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                this.continueButton.startAnimation(animationOut);
                this.continueButton.setVisibility(View.GONE);
                this.continueButton.setClickable(false);

                this.onboardingDefaultButtonsContainer.setVisibility(View.VISIBLE);
                Animation animationIn =
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                this.onboardingDefaultButtonsContainer.startAnimation(animationIn);
            }
        }
    }
    @Override
    public void onBackPressed() {
        handleBackPressed();
    }

    private void handleBackPressed() {
        if(hasPreviousPage()) {
            onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() - 1);
        }else {
            finish();
            overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        }
    }

    private boolean hasPreviousPage() {
        return onboardingViewPager.getCurrentItem() > 0;
    }
}