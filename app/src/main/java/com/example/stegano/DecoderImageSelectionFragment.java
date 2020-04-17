package com.example.stegano;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class DecoderImageSelectionFragment extends Fragment {
    private static final String TAG = "DecoderImageSelectionFr";

    private DecoderEventListener listener;

    private boolean isImageSelected = false;

    private LinearLayout imageSelectionButtonsLinearLayout;
    private LinearLayout imageSelectedButtonsLinearLayout;

    public DecoderImageSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof DecoderEventListener) {
            listener = (DecoderEventListener)activity;
        } else {
            // Throw an error!
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_decoder_image_selection, container, false);

        // Layouts
        imageSelectionButtonsLinearLayout = view.findViewById(R.id.imageSelectionButtonsLinearLayout);
        imageSelectedButtonsLinearLayout = view.findViewById(R.id.imageSelectedButtonsLinearLayout);
        imageSelectedButtonsLinearLayout.setVisibility(View.GONE);

        return view;
    }
}
