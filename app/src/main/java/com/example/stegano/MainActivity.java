package com.example.stegano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button encoderButton;
    private Button decoderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encoderButton = (Button) findViewById(R.id.encoderButton);
        decoderButton = (Button) findViewById(R.id.decoderButton);

        encoderButton.setOnClickListener(methodButtonListener);

        // TODO: Onboarding -> ois kova (View Pager)
    }

    private View.OnClickListener methodButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.encoderButton:
                    // TODO: Open encoder (View Pager)
                    return;
                case R.id.decoderButton:
                    // TODO: Open decoder (View Pager)
                    return;
                default:
                    return;
            }
        }
    };
}
