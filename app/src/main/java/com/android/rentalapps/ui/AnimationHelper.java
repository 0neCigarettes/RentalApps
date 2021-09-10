package com.android.rentalapps.ui;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationHelper {
    public interface AnimationViewListener{
        public void onAnimationEnd(Animation anim);
    }

    public static Animation getAnimation(View.OnClickListener context, int id, final AnimationViewListener animationViewListener){
        Animation animation = AnimationUtils.loadAnimation((Context) context, id);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(animationViewListener != null) animationViewListener.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;
    }
}
