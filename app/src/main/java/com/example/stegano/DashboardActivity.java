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

import com.example.stegano.decoder.DecoderActivity;
import com.example.stegano.encoder.EncoderActivity;
import com.example.stegano.util.SendDialog;

import static com.example.stegano.util.Helpers.decodeUriToBitmap;
import static com.example.stegano.util.Helpers.isNull;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";

    private Button encoderButton;
    private Button decoderButton;

    private SendDialog sendDialog;



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

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && !isNull(type)) {
            if(type.startsWith("image/")) {
                handleSendImage(intent);
            }
        }
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if(!isNull(imageUri)) {
            Bitmap bitmap = decodeUriToBitmap(this, imageUri);

            if(isNull(bitmap)) {
                // TODO: Show error message
                return;
            }

            sendDialog = new SendDialog(this);
            sendDialog.setImagePreview(bitmap);
            sendDialog.show();
        }
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
