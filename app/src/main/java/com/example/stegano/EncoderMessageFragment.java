package com.example.stegano;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class EncoderMessageFragment extends Fragment {
    private static final String TAG = "EncoderMessageFragment";
    private EventListener listener;

    Button continueButton;
    EditText messageEditText;

    String message = "";

    public EncoderMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof EventListener) {
            listener = (EventListener)activity;
        } else {
            // Throw an error!
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_encoder_message, container, false);

        continueButton = view.findViewById(R.id.continueButton);
        continueButton.setEnabled(false);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nextPage();
            }
        });

        messageEditText = view.findViewById(R.id.messageEditText);
        messageEditText.addTextChangedListener(messageChanged);

        return view;
    }

    private TextWatcher messageChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().length() == 0) {
                if(continueButton.isEnabled()) {
                    continueButton.setEnabled(false);
                    listener.messageSet(false);
                }
            } else {
                if(!continueButton.isEnabled()) {
                    continueButton.setEnabled(true);
                    listener.messageSet(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
