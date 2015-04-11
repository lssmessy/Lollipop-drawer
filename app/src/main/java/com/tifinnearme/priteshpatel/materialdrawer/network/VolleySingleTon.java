package com.tifinnearme.priteshpatel.materialdrawer.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.tifinnearme.priteshpatel.materialdrawer.material_test.MyApplication;

/**
 * Created by PRITESH on 11-04-2015.
 */
public class VolleySingleTon {
    private static VolleySingleTon sInstance=null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleySingleTon(){
        requestQueue= Volley.newRequestQueue(MyApplication.getContext());
        imageLoader=new ImageLoader(requestQueue,new ImageLoader.ImageCache() {

            private LruCache<String,Bitmap> lruCache=
                    new LruCache<String,Bitmap>((int) ((Runtime.getRuntime().maxMemory()/1024)/8));//Cache memory size
            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);//Get that image from cache if it is present in our cache memory
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url,bitmap);//if not then get from WS and put into cache

            }
        });

    }
    public static VolleySingleTon getsInstance(){
        if(sInstance==null){
            sInstance=new VolleySingleTon();
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue(){//gets the requestqueue which other class needs to use
        return requestQueue;
    }
    public ImageLoader getImageLoader(){
        return imageLoader;//returns the instance on ImageLoader for fragment and activity to use
    }

}
