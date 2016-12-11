package com.tumblrviewer.helpers;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Locale;

/**
 * Created by sebastian on 10.12.2016.
 */
public enum CustomFonts {
    SanFranciscoDisplay("SanFranciscoDisplay-Regular.ttf"),
    SanFranciscoDisplayBold("SanFranciscoDisplay-Bold.ttf");

    private final String fileName;

    CustomFonts(String fileName) {
        this.fileName = fileName;
    }

    static CustomFonts fromString(String fontName) {
        return CustomFonts.valueOf(fontName.toUpperCase(Locale.US));
    }

    public Typeface asTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), fileName);
    }
}
