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

    public InformativeDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

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

    public void setDialogTitle(String title) {
        titleValue = title;
    }

    public void setDialogDescription(String description) {
        descriptionValue = description;
    }

    public void setOkButtonTitle(String title) {
        okButtonValue = title;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

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