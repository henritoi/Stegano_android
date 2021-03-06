package com.example.stegano.encoder;

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
import androidx.fragment.app.Fragment;

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
import com.example.stegano.encoder.EncoderEventListener;

import static com.example.stegano.util.Helpers.isNull;


/**
 * Encoder: Image selection fragment
 */
public class EncoderImageSelectionFragment extends Fragment {
    private static final String TAG = "EncoderImageSelectionFr";

    private static final int CAMERA_REQUEST_CODE = 0;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int USE_CAMERA_PERMISSION_REQUEST_CODE = 2;
    private static final int READ_EXTERNAL_PERMISSION_REQUEST_CODE = 3;

    private boolean isImageSelected = false;

    private EncoderEventListener listener;

    private ImageView previewImageView;
    private Button existingImageButton;
    private Button useCameraButton;
    private Button changeImageButton;
    private Button continueButton;

    private LinearLayout imageSelectionButtonsLinearLayout;
    private LinearLayout imageSelectedButtonsLinearLayout;

    private MainApplication application;

    /**
     * Required empty constructor
     */
    public EncoderImageSelectionFragment() {
        //
    }

    /**
     * Initialize listener
     * @param activity
     */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof EncoderEventListener) {
            listener = (EncoderEventListener)activity;
        } else {
            // Throw an error!
        }
    }

    /**
     * Encoder image selection initialization
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encoder_image_selection, container, false);

        previewImageView = view.findViewById(R.id.previewImageView);

        // Layouts
        imageSelectionButtonsLinearLayout = view.findViewById(R.id.imageSelectionButtonsLinearLayout);
        imageSelectedButtonsLinearLayout = view.findViewById(R.id.imageSelectedButtonsLinearLayout);
        imageSelectedButtonsLinearLayout.setVisibility(View.GONE);

        // Buttons
        existingImageButton = view.findViewById(R.id.existingImageButton);
        useCameraButton = view.findViewById(R.id.useCameraButton);
        changeImageButton = view.findViewById(R.id.changeImageButton);
        continueButton = view.findViewById(R.id.continueButton);

        existingImageButton.setOnClickListener(handleButtonClick);
        useCameraButton.setOnClickListener(handleButtonClick);
        changeImageButton.setOnClickListener(handleButtonClick);
        continueButton.setOnClickListener(handleButtonClick);

        application = ((MainApplication) getActivity().getApplicationContext());

        if(listener.hasSendImage()) {
            Bitmap bitmap = application.getSendBitmap();
            if(!isNull(bitmap)) {
                previewImageView.setImageBitmap(bitmap);

                isImageSelected = true;
                listener.setSelectedImage(bitmap);

                imageSelectionButtonsLinearLayout.setVisibility(View.GONE);
                imageSelectedButtonsLinearLayout.setVisibility(View.VISIBLE);
            }
        }
        // Clear application send image
        application.clearSendBitmap();

        return view;
    }

    /**
     * Handle button click inside the fragment
     */
    private View.OnClickListener handleButtonClick = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          switch(v.getId()) {
              case R.id.existingImageButton:
                  Log.d(TAG, "onClick: Existing Image");
                  pickExistingImage();
                  break;
              case R.id.useCameraButton:
                  Log.d(TAG, "onClick: Use Camera");
                  useCamera();
                  break;
              case R.id.changeImageButton:
                  Log.d(TAG, "onClick: Change Image");
                  removeImageSelection();
                  break;
              case R.id.continueButton:
                  Log.d(TAG, "onClick: Continue");
                  listener.nextPage();
                  break;
          }
      }
    };

    /**
     * Handle the selection of image from local storage
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
     * Use camera to take picture
     */
    private void useCamera() {
        if(getContext().checkSelfPermission(Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[] { Manifest.permission.CAMERA },
                    USE_CAMERA_PERMISSION_REQUEST_CODE);
        }else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    /**
     * Reset image selection
     */
    private void removeImageSelection() {
        isImageSelected = false;
        listener.setSelectedImage(null);
        previewImageView.setImageResource(R.drawable.ic_image_white_50dp);

        imageSelectionButtonsLinearLayout.setVisibility(View.VISIBLE);
        imageSelectedButtonsLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Handle camera use
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE
                && resultCode == Activity.RESULT_OK
                && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            previewImageView.setImageBitmap(bitmap);

            isImageSelected = true;
            listener.setSelectedImage(bitmap);

            imageSelectionButtonsLinearLayout.setVisibility(View.GONE);
            imageSelectedButtonsLinearLayout.setVisibility(View.VISIBLE);

        }else if (requestCode == GALLERY_REQUEST_CODE
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

                imageSelectionButtonsLinearLayout.setVisibility(View.GONE);
                imageSelectedButtonsLinearLayout.setVisibility(View.VISIBLE);
            }catch (Exception e) {

            }
        }
    }

    /**
     * Check for permissions to use camera
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case USE_CAMERA_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    useCamera();
                }else {
                    // Notify user that app requires camera permission
                }
                return;
            case READ_EXTERNAL_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    pickExistingImage();
                }else {
                    // Notify user that app requires read external permission
                }
                return;
        }
    }
}
