package com.tifinnearme.priteshpatel.materialdrawer.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by pritesh.patel on 14-04-15.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, Boolean goesDown)
    {
        AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator objectTranslateY=ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown==true?-100:100,0);
        ObjectAnimator objectTranslateX=ObjectAnimator.ofFloat(holder.itemView,"translationX",-10,10,0);
        animatorSet.playTogether(objectTranslateX,objectTranslateY);
        objectTranslateX.setDuration(1000);
        objectTranslateY.setDuration(1000);
        animatorSet.start();


    }
}
