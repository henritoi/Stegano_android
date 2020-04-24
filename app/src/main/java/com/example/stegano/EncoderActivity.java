package com.example.stegano;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.IDNA;
import android.os.Bundle;

import com.example.stegano.steganografia.coders.Decoder;
import com.example.stegano.steganografia.coders.Encoder;
import com.example.stegano.steganografia.crypters.CryptionType;
import com.example.stegano.steganografia.image.BufferedImage;
import com.example.stegano.util.CustomDialog;
import com.example.stegano.util.InformativeDialog;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class EncoderActivity extends AppCompatActivity implements EncoderEventListener {
    private static final String TAG = "EncoderActivity";

    private EncoderViewPager encoderViewPager;
    private TabLayout encoderTabDots;
    private EncoderViewPagerAdapter encoderViewPagerAdapter;

    private Bitmap selectedImage = null;
    private String message = "";

    private CustomDialog cancelDialog;
    private InformativeDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            if (hasSelectedImage() || hasMessage()) {
                showCancelDialog();
            } else {
                finish();
                overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
            }
        }
    }

    private boolean hasSelectedImage() {
        return selectedImage != null;
    }

    private boolean hasMessage() {
        return message.length() > 0;
    }

    private void showCancelDialog() {
        cancelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelDialog.show();
    }

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
    public void setSelectedImage(Bitmap image) {
        selectedImage = image;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void showError(String message) {
        errorDialog.setDialogTitle(getString(R.string.encode_error_title));
        errorDialog.setDialogDescription(message);

        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorDialog.show();
    }

    @Override
    public Bitmap getSelectedImage() {
        return selectedImage;
    }

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

    // Temporary test for library
    private void testCryption(Bitmap bitmap) {
        Log.d(TAG, "testCryption: Testing coders");

        String string = "Testi1234";
        Encoder encoder = new Encoder(new BufferedImage(bitmap));

        BufferedImage encodedImage = encoder.encode(string.getBytes());

        Bitmap enBitmap = encodedImage.getBitmap();

        Decoder decoder = new Decoder(new BufferedImage(enBitmap));

        byte[] decodedMessage = decoder.decode();

        Log.d(TAG, "testCryption: Decoded:" + new String(decodedMessage));
    }
}
