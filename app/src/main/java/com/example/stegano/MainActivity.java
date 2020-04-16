package com.example.stegano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        decoderButton.setOnClickListener(methodButtonListener);

        // TODO: Onboarding -> ois kova (View Pager)
    }

    private View.OnClickListener methodButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.encoderButton:
                    intent = new Intent(MainActivity.this, EncoderActivity.class);
                    startActivity(intent);
                    return;
                case R.id.decoderButton:
                    intent = new Intent(MainActivity.this, DecoderActivity.class);
                    startActivity(intent);
                    return;
                default:
                    return;
            }
        }
    };
}
