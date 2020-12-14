package com.example.stegano.encoder;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.stegano.R;
import com.example.stegano.encoder.EncoderEventListener;


/**
 * Encoder message input fragment
 */
public class EncoderMessageFragment extends Fragment {
    private static final String TAG = "EncoderMessageFragment";
    private EncoderEventListener listener;

    Button continueButton;
    EditText messageEditText;

    String message = "";

    /**
     * Required empty constructor
     */
    public EncoderMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Listener initialization
     * @param activity
     */
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

    /**
     * Encoder: Message fragment initialization
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

    /**
     * Watch for changes in message input field
     */
    private TextWatcher messageChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().length() == 0) {
                listener.setMessage("");
                if(continueButton.isEnabled()) {
                    continueButton.setEnabled(false);
                }
            } else {
                listener.setMessage(s.toString());
                if(!continueButton.isEnabled()) {
                    continueButton.setEnabled(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
