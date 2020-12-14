package com.example.stegano.encoder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.stegano.R;
import com.example.stegano.util.CustomDialog;
import com.example.stegano.util.InformativeDialog;
import com.example.stegano.util.Variables;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import static com.example.stegano.util.Helpers.isNull;

/**
 * Main Encoder activity
 */
public class EncoderActivity extends AppCompatActivity implements EncoderEventListener {
    private static final String TAG = "EncoderActivity";

    private EncoderViewPager encoderViewPager;
    private TabLayout encoderTabDots;
    private EncoderViewPagerAdapter encoderViewPagerAdapter;

    private Bitmap selectedImage = null;
    private String message = "";

    private CustomDialog cancelDialog;
    private InformativeDialog errorDialog;

    private boolean hasSendImage = false;

    /**
     * Encoder activity onCreate: Initialize activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if has send image
        Intent intent = getIntent();
        if(intent.hasExtra(Variables.HAS_SEND_IMAGE)
                && intent.getBooleanExtra(Variables.HAS_SEND_IMAGE, false)) {
            this.hasSendImage = intent.getBooleanExtra(Variables.HAS_SEND_IMAGE, false);
        }

        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


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

        disableTabDotClicks();

        cancelDialog = new CustomDialog(this);
        errorDialog = new InformativeDialog(this);
    }

    /**
     * Handle back button press
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            handleBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle hw back button pressed
     */
    @Override
    public void onBackPressed() {
        handleBackPressed();
    }

    private void handleBackPressed() {
        if(hasPreviousPage()) {
            previousPage();
        }else {
            if (hasSelectedImage() || hasMessage()) {
                showCancelDialog();
            } else {
                finish();
                overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
            }
        }
    }

    /**
     * Check if has selected image
     * @return Boolean hasSelectedImage
     */
    private boolean hasSelectedImage() {
        return !isNull(selectedImage);
    }

    /**
     * Check if has given message
     * @return Boolean hasMessage
     */
    private boolean hasMessage() {
        return message.length() > 0;
    }

    /**
     * Show cancel dialog
     */
    private void showCancelDialog() {
        cancelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelDialog.show();
    }

    /**
     * Go to next page on encoder acvity
     */
    @Override
    public void nextPage() {
        encoderViewPager.setCurrentItem(encoderViewPager.getCurrentItem() + 1);
    }

    /**
     * Go to previous page on encoder activity
     */
    @Override
    public void previousPage() {
        if(encoderViewPager.getCurrentItem() > 0) {
            encoderViewPager.setCurrentItem(encoderViewPager.getCurrentItem() - 1);
        }
    }

    /**
     * Check if has previous page
     * @return Boolean hasPreviousPage
     */
    private boolean hasPreviousPage() {
        return encoderViewPager.getCurrentItem() > 0;
    }

    /**
     * Setter for selectedImage
     * @param image
     */
    @Override
    public void setSelectedImage(Bitmap image) {
        selectedImage = image;
    }

    /**
     * Setter for message
     * @param message
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for message
     * @return String message
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Show error dialog
     * @param message
     */
    @Override
    public void showError(String message) {
        errorDialog.setDialogTitle(getString(R.string.encode_error_title));
        errorDialog.setDialogDescription(message);

        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorDialog.show();
    }

    /**
     * Check if has sendImage
     * @return Boolean hasSendImage
     */
    @Override
    public boolean hasSendImage() {
        return this.hasSendImage;
    }

    /**
     * Getter for selectedImage
     * @return
     */
    @Override
    public Bitmap getSelectedImage() {
        return selectedImage;
    }

    /**
     * Disable Encoder pager page selection from dots
     */
    private void disableTabDotClicks() {
        encoderTabDots.clearOnTabSelectedListeners();

        LinearLayout dotStrip = ((LinearLayout) encoderTabDots.getChildAt(0));
        for(int i = 0; i < dotStrip.getChildCount(); i++) {
            dotStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }
}
