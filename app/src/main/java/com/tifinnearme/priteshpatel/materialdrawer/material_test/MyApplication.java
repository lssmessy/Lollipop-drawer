package com.tifinnearme.priteshpatel.materialdrawer.material_test;

import android.app.Application;
import android.content.Context;

/**
 * Created by PRITESH on 11-04-2015.
 */
public class MyApplication extends Application {
    private static MyApplication myInstance;

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
}
