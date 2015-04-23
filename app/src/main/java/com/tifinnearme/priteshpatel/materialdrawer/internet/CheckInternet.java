package com.tifinnearme.priteshpatel.materialdrawer.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pritesh.patel on 23-04-15.
 */
public class CheckInternet{
    private Context context;
    public CheckInternet(Context context){
        this.context=context;
    }
private ConnectivityManager connectivityManager;
    public boolean isInternetAvailable(){
        connectivityManager=(ConnectivityManager)
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
