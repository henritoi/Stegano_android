package com.example.stegano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.stegano.decoder.DecoderActivity;
import com.example.stegano.encoder.EncoderActivity;
import com.example.stegano.onboarding.OnboardingActivity;
import com.example.stegano.util.Helpers;
import com.example.stegano.util.SendDialog;
import com.example.stegano.util.Variables;

import static com.example.stegano.util.Helpers.decodeUriToBitmap;
import static com.example.stegano.util.Helpers.isNull;

/**
 * Dashboard activity: Starting screen for the application
 */
public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";

    private Button encoderButton;
    private Button decoderButton;
    private ImageView infoImageView;

    private SendDialog sendDialog;

    /**
     * Initialize activitys components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_dashboard);
        encoderButton = (Button) findViewById(R.id.encoderButton);
        decoderButton = (Button) findViewById(R.id.decoderButton);
        infoImageView = (ImageView) findViewById(R.id.infoImageView);

        encoderButton.setOnClickListener(clickListener);
        decoderButton.setOnClickListener(clickListener);

        infoImageView.setOnClickListener(clickListener);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && !isNull(type)) {
            if(type.startsWith("image/")) {
                handleSendImage(intent);
            }
        }else {
            // Open Onboarding if first time opening application
            boolean isOnboardingShown = Boolean.parseBoolean(Helpers.readSharedSetting(
                    DashboardActivity.this,
                    Variables.PREF_ONBOARDING_SHOWN,
                    "false"));
            if(!isOnboardingShown) {
                Helpers.saveSharedSetting(
                        DashboardActivity.this,
                        Variables.PREF_ONBOARDING_SHOWN,
                        "true");
                openOnboarding();
            }
        }
    }

    /**
     * Open Onboarding screen
     */
    private void openOnboarding() {
        Intent intent = new Intent(DashboardActivity.this, OnboardingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
    }

    /**
     * Handle shared image from outside of the application
     * @param intent
     */
    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if(!isNull(imageUri)) {
            Bitmap bitmap = decodeUriToBitmap(this, imageUri);

            if(isNull(bitmap)) {
                return;
            }

            sendDialog = new SendDialog(this);
            sendDialog.setImagePreview(bitmap);
            sendDialog.show();
        }
    }

    /**
     * Handle button click inside the activity
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.encoderButton:
                    intent = new Intent(DashboardActivity.this, EncoderActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                    return;
                case R.id.decoderButton:
                    intent = new Intent(DashboardActivity.this, DecoderActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                    return;
                case R.id.infoImageView:
                    openOnboarding();
                    return;
                default:
                    return;
            }
        }
    };
}
