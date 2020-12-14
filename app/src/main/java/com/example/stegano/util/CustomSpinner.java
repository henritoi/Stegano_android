package com.example.stegano.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.stegano.R;
import static com.example.stegano.util.Helpers.isNull;

/**
 * Custom spinner for type selections
 */
public class CustomSpinner extends LinearLayout {
    private static final String TAG = "CustomSpinner";

    private CharSequence[] values;
    private CharSequence defaultValue;
    private int defaultValueIndex;
    private int selectedIndex;

    private LinearLayout spinnerLayout;
    private TextView selectedTextView;

    private SelectionDialog selectionDialog;

    private CustomSpinner self;

    /**
     * Handle selection change events
     */
    public interface OnItemSelectionChangedListener {
        public void selectionChanged(CustomSpinner parent, int position);
    }

    private OnItemSelectionChangedListener listener;

    /**
     * Contructor for the custom spinner
     * @param context
     * @param attrs
     */
    public CustomSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        self = this;

        TypedArray typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomSpinner,
                0,
                0
        );

        values = typedArray.getTextArray(R.styleable.CustomSpinner_android_entries);
        defaultValueIndex = typedArray.getInt(R.styleable.CustomSpinner_defaultValue, 0);
        selectedIndex = defaultValueIndex;

        if(defaultValueIndex > values.length - 1) defaultValueIndex = 0;

        typedArray.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_spinner, this, true);

        spinnerLayout = findViewById(R.id.csLayout);
        spinnerLayout.setOnClickListener(showSelectionDialog);

        selectedTextView = findViewById(R.id.csSelectionTextview);
        selectedTextView.setText(values[defaultValueIndex]);
    }

    /**
     * Show selection options in dialog
     */
    private View.OnClickListener showSelectionDialog = new OnClickListener() {
        @Override
        public void onClick(View v) {
            selectionDialog = new SelectionDialog(getContext());
            selectionDialog.setValues(values, selectedIndex);
            selectionDialog.setOnSelectionChangedListener(
                    new SelectionDialog.OnSelectionChangedListener() {
                @Override
                public void selectionChanged(int id) {
                    selectedIndex = id;
                    selectedTextView.setText(values[id]);
                    if(!isNull(listener)) {
                        listener.selectionChanged(self, id); // Custom event listener
                    }
                }
            });
            selectionDialog.show();
        }
    };

    /**
     * Returns item in given position
     * @param position
     * @return CharSequence item
     */
    public CharSequence getItemAtPosition(int position) {
        if(position >= 0 && position < this.values.length) {
            return values[position];
        }
        return null;
    }

    /**
     * Set custom listener
     * @param listener
     */
    public void setOnItemSelectionChangedListener(OnItemSelectionChangedListener listener) {
        this.listener = listener;
    }

    /**
     * Getter for selected Item
     * @return CharSequence item
     */
    public CharSequence getSelectedItem() {
        return values[selectedIndex];
    }

}
