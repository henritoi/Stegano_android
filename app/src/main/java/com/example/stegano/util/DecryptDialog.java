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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stegano.R;
import com.example.stegano.decoder.DecodeSuccessActivity;
import com.example.stegano.steganografia.crypters.CryptionType;

public class DecryptDialog extends Dialog implements android.view.View.OnClickListener {
    private static final String TAG = "DecryptDialog";

    public DecodeSuccessActivity activity;
    public Dialog dialog;
    public Button ok, cancel;

    private Spinner cryptionMethodSpinner;
    private LinearLayout aesCryptionOptions, caesarCryptionOptions;

    private EditText aesSecretEditText, shiftKeyEditText;

    TextView infoTitle, infoDescription;

    String titleValue, descriptionValue, okButtonValue;

    public DecryptDialog(Activity activity) {
        super(activity);
        this.activity = (DecodeSuccessActivity) activity;
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

        aesSecretEditText = (EditText) findViewById(R.id.aesSecretEditText);
        shiftKeyEditText = (EditText) findViewById(R.id.shiftKeyEditText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.decryptDialogOkButton:
                if(validateOptions()) {
                    setSelectedOptions();
                }
                dismiss();
                return;
            case R.id.decryptDialogCancelButton:
                dismiss();
                return;
        };
    }

    private boolean validateOptions() {
        switch (cryptionMethodSpinner.getSelectedItem().toString()) {
            case "None":
                return true;
            case "AES":
                if(aesSecretEditText.getText().length() > 0) return true;
            case "Caesar cipher":
                if(Integer.parseInt(shiftKeyEditText.getText().toString()) >= 0) return true;
        }
        return false;
    }


    private void setSelectedOptions() {
        switch (cryptionMethodSpinner.getSelectedItem().toString()) {
            case "None":
                this.activity.setCryptionOptions(
                        CryptionType.NONE,
                        null,
                        0
                );
                this.activity.updateMessage();
                break;
            case "AES":
                this.activity.setCryptionOptions(
                        CryptionType.AES,
                        aesSecretEditText.getText().toString(),
                        0
                );
                this.activity.updateMessage();
                break;
            case "Caesar cipher":
                this.activity.setCryptionOptions(
                        CryptionType.CAESAR,
                        null,
                        Integer.parseInt(shiftKeyEditText.getText().toString())
                );
                this.activity.updateMessage();
                break;
            default:
                this.activity.setCryptionOptions(
                        CryptionType.NONE,
                        aesSecretEditText.getText().toString(),
                        Integer.parseInt(shiftKeyEditText.getText().toString())
                );
                this.activity.updateMessage();
                break;
        }
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