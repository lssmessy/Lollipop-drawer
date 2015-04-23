package com.tifinnearme.priteshpatel.materialdrawer.material_test;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by PRITESH on 11-04-2015.
 */
public class MyApplication extends Application {
/*
    public static final String API_KEY_CONSUMER="YJDVYWQBuitoJFsYC8msfU5wp";
    public static final String API_KEY_CONSUMER_SECRET="2HavxudsRow7oezzuBrmZ1A75bxRPEG5oiiBiG0hoBEWPQBoQ1";
    public static final String API_ACCESS_TOKEN="121477469-pMmEPh1Cz9OArQJy7NqjcUONBbNri1udV9Uz7X0n";
    public static final String API_ACCESS_TOKEN_SECRET="YoSrTbQxpgRCzVPZwExpcjPVg5UVr1UQ2i3SeWkhklV43";
*/
    public static final String API_KEY="ed5882c75fec0692581a1a95a9f26b11";

    private static MyApplication myInstance;
    private ConnectivityManager connectivityManager;

    @Override
    public void onCreate() {
        super.onCreate();
        myInstance=this;
    }
    public static MyApplication getMyInstance(){
        return myInstance;
    }
    public static Context getContext(){
        return myInstance.getApplicationContext();//gets the instance of this application which may require other classes
    }
    public boolean isInternetAvailable(){
        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED)

            return  true;

        else if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.DISCONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.DISCONNECTED)
        {

            return false;
        }

        return  false;
    }
}
