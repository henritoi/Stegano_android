package com.example.stegano;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class EncoderCryptionSelectionFragment extends Fragment {
    private static final String TAG = "EncoderCryptionSelectio";
    private EncoderEventListener listener;

    Button generateButton;
    Switch useEncryptionSwitch;
    Spinner encryptionTypeSpinner;
    EditText aesSecretEditText;
    EditText caesarCipherEditText;
    LinearLayout encryptionTypesLinearLayout;

    public EncoderCryptionSelectionFragment() {
        // Required empty public constructor
    }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_encoder_cryption_selection, container, false);

        generateButton = (Button) view.findViewById(R.id.generateButton);
        generateButton.setEnabled(false);

        encryptionTypeSpinner = view.findViewById(R.id.encryptionTypeSpinner);
        aesSecretEditText = view.findViewById(R.id.aesSecretEditText);
        caesarCipherEditText = view.findViewById(R.id.caesarCipherEditText);

        caesarCipherEditText.setVisibility(View.GONE);

        encryptionTypeSpinner.setOnItemSelectedListener(typeChanged);

        encryptionTypesLinearLayout = view.findViewById(R.id.encryptionTypesLinearLayout);
        encryptionTypesLinearLayout.setVisibility(View.GONE);

        useEncryptionSwitch = view.findViewById(R.id.useEncryptionSwitch);
        useEncryptionSwitch.setOnCheckedChangeListener(useEncryptionSwitchChanged);

        return view;
    }

    private AdapterView.OnItemSelectedListener typeChanged = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemSelected: " + view.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private CompoundButton.OnCheckedChangeListener useEncryptionSwitchChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                encryptionTypesLinearLayout.setVisibility(View.VISIBLE);
            }else {
                encryptionTypesLinearLayout.setVisibility(View.GONE);
            }
        }
    };
}
