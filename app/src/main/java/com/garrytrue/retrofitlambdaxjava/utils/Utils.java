package com.garrytrue.retrofitlambdaxjava.utils;


import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private final static long MILLISECONDS_IN_SECONDS = 1000;
    private Utils() {
        throw new AssertionError();
    }

    public static void hideKeyboard(Activity activity,
                                    IBinder windowToken) {
        InputMethodManager mgr =
                (InputMethodManager) activity.getSystemService
                        (Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    public static String uppercaseInput(Context context,
                                        String input) {
        if (input.isEmpty()) {
                Utils.showToast(context,
                        "no input provided");
            return null;
        } else
            // Convert the input entered by the user so it's in
            // uppercase.
            return input.toUpperCase(Locale.ENGLISH);
    }

    public static void showToast(Context context,
                                 String message) {
        Toast.makeText(context,
                message,
                Toast.LENGTH_SHORT).show();
    }
    public static String formatTemperature(float temperature) {
        return String.valueOf(Math.round(temperature)) + "°";
    }
    public static String getDayOfWeek(final long timestamp) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date(timestamp*MILLISECONDS_IN_SECONDS));
    }
}
