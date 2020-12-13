package com.example.stegano.decoder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.stegano.DashboardActivity;
import com.example.stegano.MainApplication;
import com.example.stegano.R;

import static com.example.stegano.util.Helpers.isNull;

public class DecodeSuccessActivity extends AppCompatActivity {
    private static final String TAG = "DecodeSuccessActivity";

    private TextView decodedMessageTextView;
    private Button decodeDoneButton;
    private Button decryptMessageButton;
    private String decodedMessage; // Original message

    private MainApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_success);
        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        application = ((MainApplication) getApplicationContext());

        decodedMessageTextView = (TextView) findViewById(R.id.decodedMessageTextView);
        // Make TextView Scrollable
        decodedMessageTextView.setMovementMethod(new ScrollingMovementMethod());

        decodeDoneButton = (Button) findViewById(R.id.decodeDoneButton);
        decryptMessageButton =  (Button) findViewById(R.id.decryptMessageButton);

        decodeDoneButton.setOnClickListener(handleButtonClick);
        decryptMessageButton.setOnClickListener(handleButtonClick);

        decodedMessage = application.getMessage();

        if(isNull(decodedMessage) || decodedMessage.length() < 1) {
            // Show error and go back!
        }

        decodedMessageTextView.setText(decodedMessage);
    }

    private View.OnClickListener handleButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.decryptMessageButton:
                    Intent intent = new Intent(DecodeSuccessActivity.this, DecryptMessageActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                    return;
                case R.id.decodeDoneButton:
                    application.clearMessage();
                    finish();
                    return;
            }
        }
    };

    @Override
    public void onBackPressed() {
        application.clearMessage();
        finish();
    }

}
