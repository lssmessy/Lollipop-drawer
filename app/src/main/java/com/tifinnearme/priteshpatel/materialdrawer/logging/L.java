package com.tifinnearme.priteshpatel.materialdrawer.logging;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by PRITESH on 12-04-2015.
 */
public class L {
    public static void t(Context context,String message){
        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
    }
}
