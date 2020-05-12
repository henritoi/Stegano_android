package com.example.stegano.decoder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.stegano.MainApplication;
import com.example.stegano.R;
import com.example.stegano.steganografia.crypters.CryptionType;
import com.example.stegano.util.DecryptDialog;

import static com.example.stegano.util.Helpers.isNull;

public class DecodeSuccessActivity extends AppCompatActivity {

    private TextView decodedMessageTextView;
    private Button decodeDoneButton;
    private Button decryptMessageButton;
    private String decodedMessage;

    CryptionType cryptionType = CryptionType.NONE;

    private DecryptDialog decryptDialog;

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
            // TODO: Show error and go back!
        }

        decodedMessageTextView.setText(decodedMessage);

        decryptDialog = new DecryptDialog(this);
    }

    private View.OnClickListener handleButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.decryptMessageButton:
                    decryptDialog.show();
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
