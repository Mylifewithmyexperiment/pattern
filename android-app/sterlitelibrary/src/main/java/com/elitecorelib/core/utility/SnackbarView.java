package com.elitecorelib.core.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elitecore.eliteconnectlibrary.R;
import com.elitecorelib.core.EliteSession;

/**
 * Created by harshesh.soni on 06/02/2017.
 */
public class SnackbarView extends LinearLayout {
    TextView mTextViewTitle;

    public SnackbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnackbarView(Context context, int colorcode) {
        this(context, null);
        try {
            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(Gravity.CENTER_VERTICAL);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.snackbar_view, this, true);

            mTextViewTitle = (TextView) view.findViewById(R.id.tv_message);

            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.setMargins(20, 20, 20, 20); // (left, top, right, bottom)

            mTextViewTitle.setLayoutParams(layoutParams);
            mTextViewTitle.setTextColor(colorcode);
            mTextViewTitle.setClickable(false);
            mTextViewTitle.setFocusable(false);
        } catch (Exception e) {
            EliteSession.eLog.e("SnackbarView", "Error in SnackbarView - " + e.getMessage());
        }
    }

    public void settextValue(String title) {
        mTextViewTitle.setText(title);
    }

}