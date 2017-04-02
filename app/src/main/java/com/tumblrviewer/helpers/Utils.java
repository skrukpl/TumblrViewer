package com.tumblrviewer.helpers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

import com.prashantsolanki.secureprefmanager.SecurePrefManager;


/**
 * Created by sebastian on 12.12.2016.
 */
public class Utils {
    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus()!=null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void storeInPreferences(String key, String value, Context context){
        if(key!=null && value!=null)
            SecurePrefManager.with(context)
                .set(key)
                .value(value)
                .go();
    }

    public static String getFromPreferences(String key, Context context){
        if(key!=null)
            return SecurePrefManager.with(context)
                .get(key)
                .defaultValue(".")
                .go();
        else return ".";
    }

    public static void storeInPreferencesBoolean(String key, boolean value, Context context){
        if(key!=null)
            SecurePrefManager.with(context)
                    .set(key)
                    .value(value)
                    .go();
    }

    public static boolean getFromPreferencesBoolean(String key, Context context){
        if(key!=null)
            return SecurePrefManager.with(context)
                    .get(key)
                    .defaultValue(false)
                    .go();
        else return false;
    }
}
