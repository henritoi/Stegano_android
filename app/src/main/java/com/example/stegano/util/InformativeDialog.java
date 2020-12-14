package com.example.stegano.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.stegano.R;

public class InformativeDialog extends Dialog implements android.view.View.OnClickListener {
    private static final String TAG = "InformativeDialog";

    public Activity activity;
    public Dialog dialog;
    public Button ok;

    TextView infoTitle, infoDescription;

    String titleValue, descriptionValue, okButtonValue;

    /**
     * Activity constructor
     * @param activity
     */
    public InformativeDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    /**
     * Initialize dialog components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_dialog);

        infoTitle = (TextView) findViewById(R.id.infoTitle);
        infoDescription = (TextView) findViewById(R.id.infoDescription);

        ok = (Button) findViewById(R.id.infoOkButton);

        ok.setOnClickListener(this);

        initializeTexts();

    }

    /**
     * Setter for dialog title
     * @param title
     */
    public void setDialogTitle(String title) {
        titleValue = title;
    }

    /**
     * Setter for dialog description
     * @param description
     */
    public void setDialogDescription(String description) {
        descriptionValue = description;
    }

    /**
     * Setter for OK Button title
     * @param title
     */
    public void setOkButtonTitle(String title) {
        okButtonValue = title;
    }

    /**
     * Disable button click
     * @param v
     */
    @Override
    public void onClick(View v) {
        dismiss();
    }

    /**
     * Overwrite text values that are initialized
     */
    private void initializeTexts() {
        if(titleValue != null) {
            infoTitle.setText(titleValue);
        }

        if(descriptionValue != null) {
            infoDescription.setText(descriptionValue);
        }

        if(okButtonValue != null) {
            ok.setText(okButtonValue);
        }
    }
}