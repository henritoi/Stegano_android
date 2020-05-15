package com.example.stegano.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stegano.DashboardActivity;
import com.example.stegano.MainApplication;
import com.example.stegano.R;
import com.example.stegano.decoder.DecoderActivity;
import com.example.stegano.encoder.EncoderActivity;
import com.example.stegano.steganografia.crypters.CryptionType;

import javax.security.auth.login.LoginException;

import static com.example.stegano.util.Helpers.decodeUriToBitmap;
import static com.example.stegano.util.Helpers.isNull;

public class SendDialog extends Dialog implements android.view.View.OnClickListener {
    private static final String TAG = "SendDialog";

    public Activity activity;
    public Dialog dialog;
    public Button encode, decode;
    public ImageView imagePreview;
    Bitmap previewBitmap;

    private MainApplication application;

    TextView infoTitle, infoDescription;

    public SendDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.send_dialog);

        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        encode = (Button) findViewById(R.id.sendDialogEncodeButton);
        decode = (Button) findViewById(R.id.sendDialogDecodeButton);
        imagePreview = (ImageView) findViewById(R.id.sendDialogImagePreview);

        application = ((MainApplication) this.activity.getApplicationContext());

        encode.setOnClickListener(this);
        decode.setOnClickListener(this);

        if(!isNull(previewBitmap)) {
            imagePreview.setImageBitmap(previewBitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendDialogEncodeButton:
                handleEncodeButtonClick();
                dismiss();
                return;
            case R.id.sendDialogDecodeButton:
                handleDecodeButtonClick();
                dismiss();
                return;
        };
    }

    private void handleEncodeButtonClick() {
        // Set image as global
        application.setSendBitmap(this.previewBitmap);
        // Start intent
        Intent intent = new Intent(this.activity, EncoderActivity.class);
        intent.putExtra(Variables.HAS_SEND_IMAGE, true);
        this.activity.startActivity(intent);
        this.activity.overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        return;
    }

    private void handleDecodeButtonClick() {
        // Set image as global
        application.setSendBitmap(this.previewBitmap);
        // Start intent
        Intent intent = new Intent(this.activity, DecoderActivity.class);
        intent.putExtra(Variables.HAS_SEND_IMAGE, true);
        this.activity.startActivity(intent);
        this.activity.overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        return;
    }

    public void setImagePreview(Bitmap bitmap) {
        previewBitmap = bitmap;
    }
}