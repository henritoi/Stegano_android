package com.example.stegano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button encoderButton;
    private Button decoderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status bar
        if(Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

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
