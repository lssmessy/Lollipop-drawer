package com.tifinnearme.priteshpatel.materialdrawer.tvdetails;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tifinnearme.priteshpatel.materialdrawer.R;

/**
 * Created by pritesh.patel on 17-04-15.
 */
public class TV_CustomDialog extends Dialog {
    public static ImageView imageView;
    public static TextView textView,actors,first_air_date;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TV_CustomDialog(Context context, int theme) {
        super(context);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        View v=getLayoutInflater().inflate(R.layout.custom_tv_dialog,null);
        imageView=(ImageView)v.findViewById(R.id.movie_detail_image);
        textView=(TextView)v.findViewById(R.id.movie_name);
        actors= (TextView)v.findViewById(R.id.actors);
        first_air_date= (TextView)v.findViewById(R.id.first_air_date);
        setContentView(v);

    }
}
