package com.tumblrviewer.helpers.UI;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tumblrviewer.helpers.CustomFonts;

/**
 * Created by sebastian on 10.12.2016.
 */
public class TumblrTextView extends TextView {

    public TumblrTextView(Context context) {
        super(context);
    }

    public TumblrTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        } else {
            if (this.getTypeface() != null && this.getTypeface().getStyle() == Typeface.BOLD) {
                Typeface defTypeface = CustomFonts.SanFranciscoDisplayBold.asTypeface(context);
                setTypeface(defTypeface);
            } else {
                Typeface defTypeface = CustomFonts.SanFranciscoDisplay.asTypeface(context);
                setTypeface(defTypeface);
            }
        }
    }
}


