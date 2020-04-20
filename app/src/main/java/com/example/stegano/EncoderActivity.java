package com.example.stegano;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.example.stegano.steganografia.coders.Decoder;
import com.example.stegano.steganografia.coders.Encoder;
import com.example.stegano.steganografia.image.BufferedImage;
import com.example.stegano.steganografia.utils.BitUtil;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class EncoderActivity extends AppCompatActivity implements EncoderEventListener {
    private static final String TAG = "EncoderActivity";

    private EncoderViewPager encoderViewPager;
    private TabLayout encoderTabDots;
    private EncoderViewPagerAdapter encoderViewPagerAdapter;


    private Bitmap selectedImage = null;
    private String message = "";

    private Dialog cancelDialog;

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
            if (hasSelectedImage() || hasMessage()) {
                showCancelDialog();
            } else {
                finish();
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
    public void setSelectedImage(Bitmap image) {
        selectedImage = image;

        //if(hasSelectedImage()) {
        //    testCryption(image);
        //}
    }

    @Override
    public void setMessage(String message) {
        message = message;
    }


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
