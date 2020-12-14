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

/**
 * Main decoder activity
 */
public class DecoderActivity extends AppCompatActivity implements DecoderEventListener {
    private static final String TAG = "DecoderActivity";

    private Bitmap selectedImage = null;

    private CustomDialog cancelDialog;
    private InformativeDialog errorDialog;
    private boolean hasSendImage = false;


    /**
     * Decoder activity onCreate method
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

    /**
     * Back button click handle
     * @param item
     * @return Boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            handleBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle back button pressed
     */
    @Override
    public void onBackPressed() {
        handleBackPressed();
    }

    /**
     * Handle go back
     */
    private void handleBackPressed() {
        Log.d(TAG, "handleBackPressed: " + getSelectedImage());
        if (hasSelectedImage()) {
            showCancelDialog();
        } else {
            finish();
            overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        }
    }

    /**
     * Show cancel dialog
     */
    private void showCancelDialog() {
        cancelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelDialog.show();
    }

    /**
     * Check if has selected image
     * @return Boolean
     */
    private boolean hasSelectedImage() {
        return !isNull(selectedImage);
    }

    /**
     * Setter for selected image
     * @param image
     */
    @Override
    public void setSelectedImage(Bitmap image) {
        Log.d(TAG, "setSelectedImage: " + image);
        this.selectedImage = image;
    }

    /**
     * Getter for selected image
     * @return Bitmap
     */
    @Override
    public Bitmap getSelectedImage() {
        return selectedImage;
    }

    /**
     * Shows error message dialog
     * @param message
     */
    @Override
    public void showError(String message) {
        errorDialog.setDialogTitle(getString(R.string.decode_error_title));
        errorDialog.setDialogDescription(message);

        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorDialog.show();
    }

    /**
     * Shows message not found error dialog
     */
    @Override
    public void noMessageFound() {
        errorDialog.setDialogTitle(getString(R.string.decode_error_title_no_message_found));
        errorDialog.setDialogDescription(getString(R.string.decode_error_no_message_found));

        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorDialog.show();
    }

    /**
     * Check if has SendImage
     * @return Boolean
     */
    @Override
    public boolean hasSendImage() {
        return this.hasSendImage;
    }
}
