package com.example.stegano;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class DecoderImageSelectionFragment extends Fragment {
    private static final String TAG = "DecoderImageSelectionFr";

    private Button existingImageButton;
    private Button changeImageButton;
    private Button scanButton;
    private LinearLayout imageSelectedButtonsLinearLayout;
    private ImageView previewImageView;

    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int READ_EXTERNAL_PERMISSION_REQUEST_CODE = 1;

    private boolean isImageSelected = false;

    public DecoderImageSelectionFragment() {
        // Required empty public constructor
    }


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

        existingImageButton.setOnClickListener(handleButtonClick);
        changeImageButton.setOnClickListener(handleButtonClick);
        scanButton.setOnClickListener(handleButtonClick);
        return view;
    }

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
                    // TODO
                    break;
            }
        }
    };

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

    private void removeImageSelection() {
        isImageSelected = false;
        previewImageView.setImageResource(R.drawable.ic_image_white_50dp);

        existingImageButton.setVisibility(View.VISIBLE);
        imageSelectedButtonsLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE
                && resultCode == Activity.RESULT_OK
                && data != null) {

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

                existingImageButton.setVisibility(View.GONE);
                imageSelectedButtonsLinearLayout.setVisibility(View.VISIBLE);
            }catch (Exception e) {

            }
        }
    }
}
