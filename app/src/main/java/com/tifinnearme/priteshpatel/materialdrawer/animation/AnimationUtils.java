package com.tifinnearme.priteshpatel.materialdrawer.animation;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by pritesh.patel on 14-04-15.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, Boolean goesDown)
    {
        ObjectAnimator objectTranslateY=ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown==true?300:-300,0);
        objectTranslateY.setDuration(1000);
        objectTranslateY.start();

    }
}
