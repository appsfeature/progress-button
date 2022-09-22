package com.progressbutton;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class ProgressAnim {

    public interface AnimatorListener {
        default void onAnimationStart(Animator animation) {}
        void onAnimationEnd(Animator animation);
    }

    public static void alphaAnimation(View view, int visibility) {
        alphaAnimation(view, 400, visibility, null);
    }

    public static void alphaAnimation(View view, int duration, int visibility) {
        alphaAnimation(view, duration, visibility, null);
    }

    public static void alphaAnimation(View view, int duration, int visibility, AnimatorListener animatorListener) {
        if(view == null){
            return;
        }
        AlphaAnimation alphaAnim;
        if (visibility == View.VISIBLE) {
            alphaAnim = new AlphaAnimation(0.0f, 1.0f);
        } else {
            alphaAnim = new AlphaAnimation(1.0f, 0.0f);
        }
        alphaAnim.setDuration(duration);
        alphaAnim.setFillAfter(true);
//        alphaAnim.setStartOffset(5000);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                view.setVisibility(visibility);
                if (animatorListener != null) {
                    animatorListener.onAnimationEnd(null);
                }
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                if (animatorListener != null) {
                    animatorListener.onAnimationStart(null);
                }
            }

        });
        view.setVisibility(View.VISIBLE);
        view.startAnimation(alphaAnim);
    }
}
