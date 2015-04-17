package com.tifinnearme.priteshpatel.materialdrawer.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by PRITESH on 12-04-2015.
 */
public class L {
    public static void t(Context context,String message){
        try {
            Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public static void m(String message) {
        Log.d("pritesh", "" + message);
    }
}
