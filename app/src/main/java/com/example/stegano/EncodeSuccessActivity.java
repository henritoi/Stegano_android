package com.example.stegano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.stegano.util.Variables;

import java.io.File;

public class EncodeSuccessActivity extends AppCompatActivity {
    private static final String TAG = "EncodeSuccessActivity";

    private Bitmap bitmap;
    private ImageView encodedImagePreview;
    private Uri imageUri;

    private Button encodeSuccessShareButton;

    private MainApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode_success);

        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        application = ((MainApplication) getApplicationContext());
        encodedImagePreview = findViewById(R.id.encodedImagePreview);

        encodeSuccessShareButton = findViewById(R.id.encodeSuccessShareButton);
        Button encodeSuccessDoneButton = findViewById(R.id.encodeSuccessDoneButton);

        encodeSuccessShareButton.setOnClickListener(handleButtonClick);
        encodeSuccessDoneButton.setOnClickListener(handleButtonClick);

        // Get save location
        Intent intent = getIntent();
        if(intent.hasExtra(Variables.ENCODED_BITMAP_URI)) {
            imageUri = intent.getParcelableExtra(Variables.ENCODED_BITMAP_URI);
        }

        // Get stored bitmap
        bitmap = application.getBitmap();
        // If isset -> display
        if(bitmap != null) {
            encodedImagePreview.setImageBitmap(bitmap);
        }
    }

    private View.OnClickListener handleButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.encodeSuccessShareButton:
                    Log.d(TAG, "onClick: Share");
                    shareImage();
                    break;
                case R.id.encodeSuccessDoneButton:
                    Log.d(TAG, "onClick: Done");
                    application.clearBitmap();
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        application.clearBitmap();
        finish();
    }

    private void shareImage() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(intent, getString(R.string.encode_success_share_image)));
    }
}
