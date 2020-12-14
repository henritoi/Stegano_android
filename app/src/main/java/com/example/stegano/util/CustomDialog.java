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

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {
    private static final String TAG = "CustomDialog";

    public Activity activity;
    public Dialog dialog;
    public Button yes, no;

    private String titleValue;
    private String descriptionValue;
    private String yesButtonValue;
    private String noButtonValue;

    TextView alertTitle, alertDescription;

    /**
     * Dialog initialization
     * @param activity
     */
    public CustomDialog(Activity activity) {
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
        setContentView(R.layout.alert_dialog);

        alertTitle = (TextView) findViewById(R.id.alertTitle);
        alertDescription = (TextView) findViewById(R.id.alertDescription);

        yes = (Button) findViewById(R.id.alertYesButton);
        no = (Button) findViewById(R.id.alertNoButton);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        initializeTexts();
    }

    /**
     * Handle click events inside the dialog
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alertYesButton:
                activity.finish();
                break;
            case R.id.alertNoButton:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
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
     * Setter for dialog yes button title
     * @param title
     */
    public void setYesButtonTitle(String title) {
        yesButtonValue = title;
    }

    /**
     * Setter for dialog no button title
     * @param title
     */
    public void setNoButtonTitle(String title) {
        noButtonValue = title;
    }

    /**
     * Overwrite text values if they are initialized
     */
    private void initializeTexts() {
        if(titleValue != null) {
            alertTitle.setText(titleValue);
        }

        if(descriptionValue != null) {
            alertDescription.setText(descriptionValue);
        }

        if(yesButtonValue != null) {
            yes.setText(yesButtonValue);
        }

        if(noButtonValue != null) {
            no.setText(noButtonValue);
        }
    }
}