package com.example.stegano;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.stegano.steganografia.coders.Encoder;
import com.example.stegano.steganografia.crypters.CryptionType;
import com.example.stegano.steganografia.crypters.other.CaesarCipher;
import com.example.stegano.steganografia.crypters.symmetric.AES;
import com.example.stegano.steganografia.image.BufferedImage;
import com.example.stegano.util.Variables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class EncoderCryptionSelectionFragment extends Fragment {
    private static final String TAG = "EncoderCryptionSelectio";
    private EncoderEventListener listener;

    private CryptionType cryptionType = CryptionType.NONE;

    private static final int WRITE_EXTERNAL_PERMISSION_REQUEST_CODE = 1;

    Button generateButton;
    Switch useEncryptionSwitch;
    Spinner encryptionTypeSpinner;
    EditText aesSecretEditText;
    EditText caesarCipherEditText;
    LinearLayout encryptionTypesLinearLayout;
    LinearLayout aesEncryptionLinearLayout;
    LinearLayout caesarCipherLinearLayout;

    public EncoderCryptionSelectionFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof EncoderEventListener) {
            listener = (EncoderEventListener) activity;
        } else {
            // Throw an error!
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_encoder_cryption_selection, container, false);

        generateButton = (Button) view.findViewById(R.id.generateButton);
        generateButton.setEnabled(isValid());

        generateButton.setOnClickListener(handleGenerateButtonClick);

        encryptionTypeSpinner = view.findViewById(R.id.encryptionTypeSpinner);
        aesSecretEditText = view.findViewById(R.id.aesSecretEditText);
        caesarCipherEditText = view.findViewById(R.id.caesarCipherEditText);

        aesEncryptionLinearLayout = view.findViewById(R.id.aesEncryptionLinearLayout);
        caesarCipherLinearLayout = view.findViewById(R.id.caesarCipherLinearLayout);

        aesSecretEditText.addTextChangedListener(aesSecretTextChanged);
        caesarCipherEditText.addTextChangedListener(caesarShiftKeyChanged);

        caesarCipherLinearLayout.setVisibility(View.GONE);

        encryptionTypeSpinner.setOnItemSelectedListener(typeChanged);

        encryptionTypesLinearLayout = view.findViewById(R.id.encryptionTypesLinearLayout);
        encryptionTypesLinearLayout.setVisibility(View.GONE);

        useEncryptionSwitch = view.findViewById(R.id.useEncryptionSwitch);
        useEncryptionSwitch.setOnCheckedChangeListener(useEncryptionSwitchChanged);

        return view;
    }

    private void generateImage() {
        if(isValid()) {
            // Get image with listener
            Bitmap startImage = listener.getSelectedImage();

            if(startImage == null) {
                listener.showError(getString(R.string.encode_error_image_selection));
                return;
            }

            // Get message with listener
            String message = listener.getMessage();
            Log.d(TAG, "onClick: " + message);

            if(message.length() < 1) {
                listener.showError(getString(R.string.encode_error_get_message));
                return;
            }

            // Initialize Encoder
            Encoder encoder = new Encoder(new BufferedImage(startImage));

            // Encrypt message
            byte[] encryptedMessage = encryptMessage(message);

            if(encryptedMessage == null) {
                listener.showError(getString(R.string.encode_error_message_encryption));
                return;
            }

            // Encode message to image
            Bitmap encodedImage = encoder.encode(encryptedMessage).getBitmap();

            if(encodedImage == null) {
                listener.showError(getString(R.string.encode_error_image_encode));
                return;
            }

            // TODO: Save image to stegano's dir
            String filename = System.currentTimeMillis() + ".png";
            Uri location = saveImage(encodedImage, filename);

            if(location == null) {
                listener.showError(getString(R.string.encode_error_image_save));
                return;
            }

            MainApplication application = ((MainApplication) getActivity().getApplicationContext());
            application.setBitmap(encodedImage);

            // Redirect to success with url to image location
            Intent intent = new Intent(getActivity(), EncodeSuccessActivity.class);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Variables.ENCODED_BITMAP_URI, location);
            getActivity().finish();
            startActivity(intent);
        }
    }

    private View.OnClickListener handleGenerateButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        WRITE_EXTERNAL_PERMISSION_REQUEST_CODE);
            }else {
                generateImage();
            }
        }
    };

    private Uri saveImage(Bitmap bitmap, String filename) {
        if(bitmap == null) return null;

        boolean saved;
        OutputStream outputStream;
        Uri imageUri;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContext().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + Variables.IMAGES_FOLDER_NAME);
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                outputStream = resolver.openOutputStream(imageUri);
            } catch (Exception e) {
                Log.e(TAG, "saveImage: ", e);
                return null; // Error shown when null
            }
        }else {
            String imagesDirectory = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString()
                    + File.separator
                    + Variables.IMAGES_FOLDER_NAME;
            File file = new File(imagesDirectory);

            if(!file.exists()) {
                file.mkdir();
            }

            File image = new File(imagesDirectory, filename);
            try {
                outputStream = new FileOutputStream(image);
                imageUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", image);
            }catch(Exception e) {
                Log.e(TAG, "saveImage: ", e);
                return null;
            }
        }

        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        try {
            outputStream.flush();
            outputStream.close();
        }catch (Exception e) {
            Log.e(TAG, "saveImage: ", e);
            return null;
        }
        return saved ? imageUri : null;
    }

    private byte[] encryptMessage(String message) {
        switch (cryptionType) {
            case NONE:
                return message.getBytes();
            case AES:
                String secret = aesSecretEditText.getText().toString();
                try {
                    AES aes = new AES(secret);
                    return aes.encrypt(message);
                }catch (Exception e) {
                    return null;
                }
            case CAESAR:
                int shiftValue = Integer.parseInt(caesarCipherEditText.getText().toString());
                CaesarCipher caesarCipher = new CaesarCipher(shiftValue);
                return caesarCipher.encrypt(message).getBytes();
        }
        return null;
    }

    private AdapterView.OnItemSelectedListener typeChanged = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemSelected: " + view.getId());
            // TODO: Spinner selected -> change inputs -> inform listener
            switch (parent.getItemAtPosition(position).toString()) {
                case "AES":
                    cryptionType = CryptionType.AES;
                    aesEncryptionLinearLayout.setVisibility(View.VISIBLE);
                    caesarCipherLinearLayout.setVisibility(View.GONE);
                    validate();
                    break;
                case "Caesar cipher":
                    cryptionType = CryptionType.CAESAR;
                    aesEncryptionLinearLayout.setVisibility(View.GONE);
                    caesarCipherLinearLayout.setVisibility(View.VISIBLE);
                    validate();
                    break;
                default:
                    cryptionType = CryptionType.NONE;
                    validate();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private CryptionType getSelectedCryptionType() {
        switch(encryptionTypeSpinner.getSelectedItem().toString()) {
            case "AES":
                return CryptionType.AES;
            case "Caesar cipher":
                return CryptionType.CAESAR;
            default:
                return CryptionType.NONE;
        }
    }

    private CompoundButton.OnCheckedChangeListener useEncryptionSwitchChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                cryptionType = getSelectedCryptionType();

                encryptionTypesLinearLayout.setVisibility(View.VISIBLE);
                validate();
            }else {
                cryptionType = CryptionType.NONE;

                encryptionTypesLinearLayout.setVisibility(View.GONE);
                validate();
            }
        }
    };

    private TextWatcher aesSecretTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            validate();
        }
    };

    private TextWatcher caesarShiftKeyChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            validate();
        }
    };

    private void validate() {
        if(isValid()) {
            generateButton.setEnabled(true);
        }else {
            generateButton.setEnabled(false);
        }
    }

    private boolean isValid() {
        if(cryptionType == CryptionType.NONE) return true;

        if(cryptionType == CryptionType.AES) {
            if(aesSecretEditText.getText().toString().length() > 0) return true;
        }

        if(cryptionType == CryptionType.CAESAR) {
            if(caesarCipherEditText.getText().toString().length() > 0
                && Integer.parseInt(caesarCipherEditText.getText().toString()) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    generateImage();
                }else {
                    listener.showError(getString(R.string.encode_error_write_permission_required));
                }
                return;
        }
    }
}
