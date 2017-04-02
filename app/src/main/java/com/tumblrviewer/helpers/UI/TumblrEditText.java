package com.tumblrviewer.helpers.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tumblrviewer.R;
import com.tumblrviewer.helpers.CustomFonts;

import java.lang.reflect.Field;


/**
 * Created by sebastian on 10.12.2016.
 */
public class TumblrEditText extends EditText {

    public TumblrEditText(Context context) {
        super(context);
    }

    public TumblrEditText(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TumblrEditText, 0, 0);
        String str = a.getString(R.styleable.TumblrEditText_show_tint);
        if (str == null) {
            tintWidget(this, ContextCompat.getColor(context, R.color.defColorEditText));
        } else {
            Log.e("STR-VAL", str);
        }
        str = a.getString(R.styleable.TumblrEditText_def_color);
        if (str == null)
            this.setTextColor(ContextCompat.getColor(context, R.color.whiteSimple));

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

    public static void tintWidget(View view, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(view.getBackground());
        DrawableCompat.setTint(wrappedDrawable.mutate(), color);
        view.setBackground(wrappedDrawable);
    }

}
