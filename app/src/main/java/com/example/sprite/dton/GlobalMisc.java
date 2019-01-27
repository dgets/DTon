package com.example.sprite.dton;

import android.content.Context;
import android.widget.Toast;

/**
 * Anything that may be necessary on a global basis; constants, variables,
 * wrappers, etc...
 */
public class GlobalMisc {
    public static int       MaxPresets = 10;
    public static Boolean   Debugging = true;

    /**
     * Wrapper for the Toast.* debaucle necessary to check for the debug
     * status of the code at this point and to make & show the Toast
     * message to the user.
     *
     * @param context current context (derp)
     * @param msg debugging message to display
     */
    public static void debugTMsg(Context context, String msg) {
        if (Debugging) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
