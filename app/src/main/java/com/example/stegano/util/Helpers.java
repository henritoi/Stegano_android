package com.example.stegano.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.ImageDecoder.Source;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

/**
 * Contains general helping methods
 */
public class Helpers {
    private static final String TAG = "Helpers";
    /**
     * Return whether given argument is null
     * @param element
     * @param <E>
     * @return Boolean isNull
     */
    public static <E> boolean isNull(E element) {
        return element == null;
    }

    public static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap bitmap = null;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), sendUri);
            } catch (IOException e) {
                Log.e(TAG, "decodeUriToBitmap: ", e);
                return null;
            }
        }else {
            try {
                Source source = ImageDecoder.createSource(mContext.getContentResolver(), sendUri);
                bitmap = ImageDecoder.decodeBitmap(source);
            } catch (Exception e) {
                Log.e(TAG, "decodeUriToBitmap: ", e);
                return null;
            }
        }
        return bitmap;
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(Variables.PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(Variables.PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }


}
