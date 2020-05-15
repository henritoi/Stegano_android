package com.example.stegano.decoder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.stegano.R;
import com.example.stegano.util.CustomDialog;
import com.example.stegano.util.InformativeDialog;
import com.example.stegano.util.Variables;

import static com.example.stegano.util.Helpers.isNull;

public class DecoderActivity extends AppCompatActivity implements DecoderEventListener {
    private static final String TAG = "DecoderActivity";

    private Bitmap selectedImage = null;

    private CustomDialog cancelDialog;
    private InformativeDialog errorDialog;
    private boolean hasSendImage = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if has send image
        Intent intent = getIntent();
        if(intent.hasExtra(Variables.HAS_SEND_IMAGE)
                && intent.getBooleanExtra(Variables.HAS_SEND_IMAGE, false)) {
            this.hasSendImage = intent.getBooleanExtra(Variables.HAS_SEND_IMAGE, false);
        }

        setContentView(R.layout.activity_decoder);

        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cancelDialog = new CustomDialog(this);
        errorDialog = new InformativeDialog(this);

        Log.d(TAG, "onCreate: " + hasSendImage);
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
        Log.d(TAG, "handleBackPressed: " + getSelectedImage());
        if (hasSelectedImage()) {
            showCancelDialog();
        } else {
            finish();
            overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        }
    }

    private void showCancelDialog() {
        cancelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelDialog.show();
    }

    private boolean hasSelectedImage() {
        return !isNull(selectedImage);
    }

    @Override
    public void setSelectedImage(Bitmap image) {
        Log.d(TAG, "setSelectedImage: " + image);
        this.selectedImage = image;
    }

    @Override
    public Bitmap getSelectedImage() {
        return selectedImage;
    }

    @Override
    public void showError(String message) {
        errorDialog.setDialogTitle(getString(R.string.decode_error_title));
        errorDialog.setDialogDescription(message);

        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorDialog.show();
    }

    @Override
    public void noMessageFound() {
        errorDialog.setDialogTitle(getString(R.string.decode_error_title_no_message_found));
        errorDialog.setDialogDescription(getString(R.string.decode_error_no_message_found));

        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorDialog.show();
    }

    @Override
    public boolean hasSendImage() {
        return this.hasSendImage;
    }
}
