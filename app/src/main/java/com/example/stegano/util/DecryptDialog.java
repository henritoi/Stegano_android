package com.example.stegano.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stegano.R;
import com.example.stegano.steganografia.crypters.CryptionType;

public class DecryptDialog extends Dialog implements android.view.View.OnClickListener {
    private static final String TAG = "DecryptDialog";

    public Activity activity;
    public Dialog dialog;
    public Button ok, cancel;

    private Spinner cryptionMethodSpinner;
    private LinearLayout aesCryptionOptions, caesarCryptionOptions;

    TextView infoTitle, infoDescription;

    String titleValue, descriptionValue, okButtonValue;

    public DecryptDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.decrypt_dialog);

        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ok = (Button) findViewById(R.id.decryptDialogOkButton);
        cancel = (Button) findViewById(R.id.decryptDialogCancelButton);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

        cryptionMethodSpinner = (Spinner) findViewById(R.id.decryptDialogCryptionMethodSpinner);
        cryptionMethodSpinner.setOnItemSelectedListener(decryptMethodChanged);

        aesCryptionOptions = (LinearLayout) findViewById(R.id.aesCryptionOptions);
        caesarCryptionOptions = (LinearLayout) findViewById(R.id.caesarCryptionOptions);

        aesCryptionOptions.setVisibility(View.GONE);
        caesarCryptionOptions.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.decryptDialogOkButton:
                dismiss();
                return;
            case R.id.decryptDialogCancelButton:
                dismiss();
                return;
        };
    }

    private AdapterView.OnItemSelectedListener decryptMethodChanged =
            new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemSelected: " + parent.getItemAtPosition(position).toString());
            switch (parent.getItemAtPosition(position).toString()) {
                case "None":
                    aesCryptionOptions.setVisibility(View.GONE);
                    caesarCryptionOptions.setVisibility(View.GONE);
                case "AES":
                    aesCryptionOptions.setVisibility(View.VISIBLE);
                    caesarCryptionOptions.setVisibility(View.GONE);
                    break;
                case "Caesar cipher":
                    aesCryptionOptions.setVisibility(View.GONE);
                    caesarCryptionOptions.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}