package com.example.stegano;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class EncoderActivity extends AppCompatActivity implements EncoderEventListener {
    private static final String TAG = "EncoderActivity";

    private EncoderViewPager encoderViewPager;
    private TabLayout encoderTabDots;
    private EncoderViewPagerAdapter encoderViewPagerAdapter;

    private boolean isImageSelected = false;
    private boolean isMessageSet = false;

    private Dialog cancelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide status bar
        if(Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        setContentView(R.layout.activity_encoder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        encoderViewPager = findViewById(R.id.encoderViewPager);
        encoderViewPagerAdapter = new EncoderViewPagerAdapter(getSupportFragmentManager());
        encoderViewPager.setAdapter(encoderViewPagerAdapter);
        encoderViewPager.setOffscreenPageLimit(encoderViewPagerAdapter.getCount() - 1); // Default 1

        encoderTabDots = findViewById(R.id.encoderTabDots);
        encoderTabDots.setupWithViewPager(encoderViewPager, true);

        cancelDialog = new Dialog(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            handleBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        handleBackPressed();
    }

    private void handleBackPressed() {
        if(hasPreviousPage()) {
            previousPage();
        }else {
            if (isImageSelected || isMessageSet) {
                showCancelDialog();
            } else {
                finish();
            }
        }
    }

    private void showCancelDialog() {
        Button alertCancelButton;
        Button alertContinueButton;

        cancelDialog.setContentView(R.layout.alert_dialog);

        alertCancelButton = (Button) cancelDialog.findViewById(R.id.alertCancelButton);
        alertContinueButton = (Button) cancelDialog.findViewById(R.id.alertContinueButton);

        alertCancelButton.setOnClickListener(handleAlertClick);
        alertContinueButton.setOnClickListener(handleAlertClick);

        cancelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelDialog.show();
    }

    private View.OnClickListener handleAlertClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.alertCancelButton:
                    cancelDialog.dismiss();
                    finish();
                    return;
                case R.id.alertContinueButton:
                    cancelDialog.dismiss();
                    return;
            }
        }
    };

    @Override
    public void nextPage() {
        encoderViewPager.setCurrentItem(encoderViewPager.getCurrentItem() + 1);
    }

    @Override
    public void previousPage() {
        if(encoderViewPager.getCurrentItem() > 0) {
            encoderViewPager.setCurrentItem(encoderViewPager.getCurrentItem() - 1);
        }
    }

    private boolean hasPreviousPage() {
        return encoderViewPager.getCurrentItem() > 0;
    }

    @Override
    public void imageSelected(boolean isSelected) {
        isImageSelected = isSelected;
    }

    @Override
    public void messageSet(boolean isSet) {
        isMessageSet = isSet;
    }
}
