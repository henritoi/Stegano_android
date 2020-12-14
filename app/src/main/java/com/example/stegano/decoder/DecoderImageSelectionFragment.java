package com.example.stegano.decoder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.stegano.MainApplication;
import com.example.stegano.R;
import com.example.stegano.steganografia.coders.Decoder;
import com.example.stegano.steganografia.image.BufferedImage;
import com.example.stegano.util.FullScreenDialog;
import com.example.stegano.util.Variables;

import java.util.concurrent.TimeUnit;

import static com.example.stegano.util.Helpers.isNull;


/**
 * Fragment for Decoder in which image selection is handled
 */
public class DecoderImageSelectionFragment extends Fragment {
    private static final String TAG = "DecoderImageSelectionFr";

    private Button existingImageButton;
    private Button changeImageButton;
    private Button scanButton;
    private LinearLayout imageSelectedButtonsLinearLayout;
    private ImageView previewImageView;

    private DecoderEventListener listener;

    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int READ_EXTERNAL_PERMISSION_REQUEST_CODE = 1;

    private DialogFragment loadingScreen;

    private boolean isImageSelected = false;

    private MainApplication application;

    /**
     * Empty constructor
     */
    public DecoderImageSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Event listener initialization
     * @param activity
     */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof DecoderEventListener) {
            listener = (DecoderEventListener) activity;
        } else {
            // Throw an error!
        }
    }

    /**
     * Fragment onCreateView: View component initialization
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_decoder_image_selection, container, false);

        existingImageButton = view.findViewById(R.id.existingImageButton);
        changeImageButton = view.findViewById(R.id.changeImageButton);
        scanButton = view.findViewById(R.id.scanButton);
        imageSelectedButtonsLinearLayout = view.findViewById(R.id.imageSelectedButtonsLinearLayout);
        imageSelectedButtonsLinearLayout.setVisibility(View.GONE);

        previewImageView = view.findViewById(R.id.previewImageView);

        loadingScreen = FullScreenDialog.newInstance();

        existingImageButton.setOnClickListener(handleButtonClick);
        changeImageButton.setOnClickListener(handleButtonClick);
        scanButton.setOnClickListener(handleButtonClick);

        application = ((MainApplication) getActivity().getApplicationContext());

        if(listener.hasSendImage()) {
            Bitmap bitmap = application.getSendBitmap();
            if(!isNull(bitmap)) {
                previewImageView.setImageBitmap(bitmap);

                isImageSelected = true;
                listener.setSelectedImage(bitmap);

                existingImageButton.setVisibility(View.GONE);
                imageSelectedButtonsLinearLayout.setVisibility(View.VISIBLE);
            }
        }

        // Clear application send image
        application.clearSendBitmap();

        return view;
    }

    /**
     * Handle button clicks in this fragment
     */
    private View.OnClickListener handleButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.existingImageButton:
                    Log.d(TAG, "onClick: Existing Image");
                    pickExistingImage();
                    break;
                case R.id.changeImageButton:
                    Log.d(TAG, "onClick: Change Image");
                    removeImageSelection();
                    break;
                case R.id.scanButton:
                    Log.d(TAG, "onClick: Scan");
                    decodeImage();
                    break;
            }
        }
    };

    /**
     * Handle image selection from gallery
     */
    private void pickExistingImage() {
        if(getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    READ_EXTERNAL_PERMISSION_REQUEST_CODE);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        }
    }

    /**
     * Reset image selection process
     */
    private void removeImageSelection() {
        isImageSelected = false;
        listener.setSelectedImage(null);
        previewImageView.setImageResource(R.drawable.ic_image_white_50dp);

        existingImageButton.setVisibility(View.VISIBLE);
        imageSelectedButtonsLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Handle Image selection from gallery
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE
                && resultCode == Activity.RESULT_OK
                && !isNull(data)) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(
                    selectedImage,
                    filePathColumn,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();

            try {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                previewImageView.setImageBitmap(bitmap);

                isImageSelected = true;
                listener.setSelectedImage(bitmap);

                existingImageButton.setVisibility(View.GONE);
                imageSelectedButtonsLinearLayout.setVisibility(View.VISIBLE);
            }catch (Exception e) {

            }
        }
    }

    /**
     * Show loading animation
     */
    private void showLoader() {
        loadingScreen.show(getActivity().getSupportFragmentManager(), "loader");
    }

    /**
     * Hide loading animation
     */
    private void hideLoader() {
        loadingScreen.dismiss();
    }

    /**
     * Handle image decode and if message was not found
     */
    private void decodeImage() {
        final Bitmap bitmap = listener.getSelectedImage();

        if(isNull(bitmap)) {
            removeImageSelection(); // Start again
            listener.showError(getString(R.string.decode_error_image_not_selected));
            return;
        }

        showLoader();

        // To show scanning screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Decoder decoder = new Decoder(new BufferedImage(bitmap));

                int messageLength = decoder.decodeMessageLength();

                if(messageLength <= 0) {
                    hideLoader();
                    removeImageSelection();
                    listener.noMessageFound();
                    return;
                }

                Log.d(TAG, "decodeImage: " + messageLength);

                byte[] message;

                try {
                    // For safety reasons
                    message = decoder.decode();
                }catch (Exception e) {
                    hideLoader();
                    removeImageSelection();
                    listener.noMessageFound();
                    return;
                }

                if(message.length < 1) {
                    hideLoader();
                    removeImageSelection();
                    listener.noMessageFound();
                    return;
                }
                // Store message to global
                application.setMessage(new String(message));
                Intent intent = new Intent(getActivity(), DecodeSuccessActivity.class);
                getActivity().finish();
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                return;
            }
        }, 2000);
    }
}
