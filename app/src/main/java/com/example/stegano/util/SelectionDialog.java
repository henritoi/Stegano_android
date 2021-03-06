package com.example.stegano.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.stegano.R;

import static com.example.stegano.util.Helpers.isNull;

/**
 * Used for custom spinner
 */
public class SelectionDialog extends Dialog {
    private static final String TAG = "SelectionDialog";

    private CharSequence[] values;
    private int selectedIndex;

    private LinearLayout selectionOptions;

    public interface OnSelectionChangedListener {
        public void selectionChanged(int id);
    }

    public OnSelectionChangedListener listener;

    /**
     * Contructor for selection dialog
     * @param context
     */
    public SelectionDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * Initialize dialog components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_spinner_selection_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        selectionOptions = findViewById(R.id.csSelectionDialogOptions);

        initSelectionItems();
    }

    /**
     * Set all values at the same time
     * @param values
     * @param selected
     */
    public void setValues(CharSequence[] values, int selected) {
        Log.d(TAG, "setValues: " + selected);
        this.values = values;
        this.selectedIndex = selected;
    }

    /**
     * Initialize selection items
     */
    private void initSelectionItems() {
        if(!isNull(values) && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                selectionOptions.addView(generateItem(values[i].toString(), i));
            }
        }
    }

    /**
     * Generate a single item to the dialog
     * @param title
     * @param id
     * @return
     */
    private Button generateItem(String title, int id) {
        Log.d(TAG, "generateItem: " + (id == selectedIndex ? "True" : "False"));
        int itemStyle = id == selectedIndex
                ? R.style.SelectionDialogItem_Selected
                : R.style.SelectionDialogItem;

        Button item = new Button(
                new ContextThemeWrapper(getContext(), itemStyle), null, itemStyle);
        item.setId(id);
        item.setText(title);
        item.setOnClickListener(itemSelected);

        return item;
    }

    /**
     * Handle item selected event
     */
    private View.OnClickListener itemSelected = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!isNull(listener)) {
                listener.selectionChanged(v.getId());
            }
            dismiss();
        }
    };

    /**
     * Set listener to listen selection change event
     * @param listener
     */
    public void setOnSelectionChangedListener(OnSelectionChangedListener listener) {
        this.listener = listener;
    }
}
