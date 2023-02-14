package com.progressbutton.base;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.progressbutton.ProgressButton;

public class ProgressAnim {


    public void startAlphaAnimation(View v, int visibility) {
        startAlphaAnimation(v, visibility, null);
    }

    public void startAlphaAnimation(View v, int visibility, ProgressButton.Listener listener) {
        startAlphaAnimation(v, 10, visibility, listener);
    }

    public void startAlphaAnimation(final View v, long duration, final int visibility) {
        startAlphaAnimation(v, duration, visibility, null);
    }
    public void startAlphaAnimation(final View v, long duration, final int visibility, ProgressButton.Listener listener) {
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
                if (listener != null) {
                    listener.onAnimationCompleted();
                }
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
