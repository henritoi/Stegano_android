package com.example.stegano.decoder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.stegano.MainApplication;
import com.example.stegano.R;
import com.example.stegano.steganografia.crypters.CryptionType;
import com.example.stegano.steganografia.crypters.other.CaesarCipher;
import com.example.stegano.steganografia.crypters.symmetric.AES;
import com.example.stegano.util.CustomSpinner;

/**
 * Decoder: Decrypt found message
 */
public class DecryptMessageActivity extends AppCompatActivity{
    private static final String TAG = "DecryptMessageActivity";

    private TextView decryptedMessageTextView;

    private CryptionType cryptionType = CryptionType.AES;

    private MainApplication application;
    private String decodedMessage = "";

    CustomSpinner decryptionTypeSpinner;
    EditText aesSecretEditText;
    EditText caesarCipherEditText;
    LinearLayout decryptionTypesLinearLayout;
    LinearLayout aesDecryptionLinearLayout;
    LinearLayout caesarCipherLinearLayout;
    Button doneButton;

    /**
     * Decode decrypt activity onCreate: Initialize activity components
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt_message);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        application = ((MainApplication) getApplicationContext());

        decryptedMessageTextView = (TextView) findViewById(R.id.decryptedMessage);

        decodedMessage = application.getMessage();
        decryptedMessageTextView.setText(decodedMessage);

        decryptionTypeSpinner = findViewById(R.id.decryptionTypeSpinner);
        aesSecretEditText = findViewById(R.id.aesSecretEditText);
        caesarCipherEditText = findViewById(R.id.caesarCipherEditText);

        aesDecryptionLinearLayout = findViewById(R.id.aesDecryptionLinearLayout);
        caesarCipherLinearLayout = findViewById(R.id.caesarCipherLinearLayout);

        aesSecretEditText.addTextChangedListener(aesSecretTextChanged);
        caesarCipherEditText.addTextChangedListener(caesarShiftKeyChanged);

        caesarCipherLinearLayout.setVisibility(View.GONE);

        decryptionTypeSpinner.setOnItemSelectionChangedListener(typeChanged);

        decryptionTypesLinearLayout = findViewById(R.id.decryptionTypesLinearLayout);

        doneButton = (Button) findViewById(R.id.done);
        doneButton.setOnClickListener(handleDoneButtonClick);
    }

    /**
     * Watch changes in aesSecretText input
     */
    private TextWatcher aesSecretTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            decryptMessage();
        }
    };
    /**
     * Watch changes in caesarShiftKey input
     */
    private TextWatcher caesarShiftKeyChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            decryptMessage();
        }
    };

    /**
     * Listen changes in Custom spinner menu and handle cryptiontype change
     */
    private CustomSpinner.OnItemSelectionChangedListener typeChanged
            = new CustomSpinner.OnItemSelectionChangedListener() {

        @Override
        public void selectionChanged(CustomSpinner parent, int position) {
            switch (parent.getItemAtPosition(position).toString()) {
                case "AES":
                    cryptionType = CryptionType.AES;
                    aesDecryptionLinearLayout.setVisibility(View.VISIBLE);
                    caesarCipherLinearLayout.setVisibility(View.GONE);
                    decryptMessage();
                    break;
                case "Caesar cipher":
                    cryptionType = CryptionType.CAESAR;
                    aesDecryptionLinearLayout.setVisibility(View.GONE);
                    caesarCipherLinearLayout.setVisibility(View.VISIBLE);
                    decryptMessage();
                    break;
                default:
                    cryptionType = CryptionType.NONE;
                    decryptMessage();
                    break;
            }
        }
    };

    /**
     * Decrypt message with given parameters
     */
    private void decryptMessage() {
        Log.d(TAG, "decryptMessage: DECRYPTING MESSAGE: " + this.decodedMessage);
        Log.d(TAG, "decryptMessage: " + cryptionType);
        switch (this.cryptionType) {
            case AES:
                String secret = aesSecretEditText.getText().toString();
                Log.d(TAG, "decryptMessage: " + secret);
                try {
                    AES aes = new AES(secret);
                    Log.d(TAG, "decryptMessage: " + aes.decrypt(this.decodedMessage));
                    this.decryptedMessageTextView.setText(aes.decrypt(this.decodedMessage));
                }catch (Exception e) {
                    // Show error
                }
                break;
            case CAESAR:
                String shiftString = caesarCipherEditText.getText().toString();
                int shiftValue = shiftString.length() > 0
                        ? Integer.parseInt(shiftString)
                        : 0;
                CaesarCipher caesarCipher = new CaesarCipher(shiftValue);
                this.decryptedMessageTextView.setText(caesarCipher.decrypt(this.decodedMessage));
                break;
            default:
                this.decryptedMessageTextView.setText(decodedMessage);
                break;
        }
    }

    /**
     * Handle done button click
     */
    private View.OnClickListener handleDoneButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            application.clearMessage();
            finish();
        }
    };
}