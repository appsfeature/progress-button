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


    public void startAlphaAnimation(View v, int visibility) {
        startAlphaAnimation(v, 10, visibility);
    }

    public void startAlphaAnimation(final View v, long duration, final int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (visibility == View.VISIBLE) {
                    v.setVisibility(visibility);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(visibility);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
