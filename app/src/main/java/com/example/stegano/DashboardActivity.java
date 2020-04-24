package com.example.stegano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";

    private Button encoderButton;
    private Button decoderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_dashboard);
        encoderButton = (Button) findViewById(R.id.encoderButton);
        decoderButton = (Button) findViewById(R.id.decoderButton);

        encoderButton.setOnClickListener(methodButtonListener);
        decoderButton.setOnClickListener(methodButtonListener);

        // TODO: Onboarding -> ois kova (View Pager)
    }

    private View.OnClickListener methodButtonListener = new View.OnClickListener() {
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
                default:
                    return;
            }
        }
    };
}
