package com.progressbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Created by Admin on 8/28/2017.
 */
public class ProgressButton {

    //================================================================================
    // Properties
    //================================================================================
    private static final long BUTTON_PROGRESS_TIME = 500;
    private final ProgressAnim mAnim;
    private long expandCollapseTime = 400;
    private final ProgressBar pBar;
    private final Button btnAction;
    private final ImageView ivStatus;
    private final Context context;
    private int mOriginalWidth, mOriginalHeight;
    private String text;
    private AnimatorListener mProgressListener;

    //================================================================================
    // Constructors
    //================================================================================
    public interface Listener {
        void onAnimationCompleted();
    }

    public interface AnimatorListener {
        default void onAnimationStart(Animator animation) {}
        void onAnimationEnd(Animator animation);
    }

    public static ProgressButton newInstance(Activity activity) {
        return new ProgressButton(activity, activity.getWindow().getDecorView().getRootView());
    }

    public static ProgressButton newInstance(Context context, View v) {
        return new ProgressButton(context, v);
    }

    private ProgressButton(Context context, View view) {
        this.context = context;
        this.mAnim = new ProgressAnim();
        ivStatus = view.findViewById(R.id.iv_status);
        pBar = view.findViewById(R.id.progressBar);
        btnAction = view.findViewById(R.id.btn_action);
        btnAction.post(new Runnable() {
            @Override
            public void run() {
                mOriginalWidth = btnAction.getWidth();
                mOriginalHeight = btnAction.getHeight();
            }
        });
    }

    //================================================================================
    // Accessors
    //================================================================================
    public ProgressButton setOnClickListener(View.OnClickListener click) {
        if (btnAction != null) {
            btnAction.setOnClickListener(click);
        }
        return this;
    }


    public ProgressButton setOnEditorActionListener(EditText editText) {
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle pressing "Enter" key here
                    executeTask();
                    handled = true;
                }
                return handled;
            }
        });
        return this;
    }

    public ProgressButton setOnEditorActionListener(EditText editText, String btnLabel) {
        editText.setImeActionLabel(btnLabel, EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle pressing "Enter" key here
                    executeTask();
                    handled = true;
                }
                return handled;
            }
        });
        return this;
    }

    public ProgressButton setTransparentBackground(int textColor) {
        if (btnAction != null) {
            btnAction.setBackgroundResource(getColorRes(context, android.R.color.transparent));
            pBar.setIndeterminateDrawable(getDrawableRes(context, R.drawable.pb_progress_custom_accent));
            ivStatus.setBackgroundResource(getColorRes(context, android.R.color.transparent));
            ivStatus.setColorFilter(getColorRes(context, R.color.colorAccent));
            btnAction.setTextColor(textColor);
        }
        return this;
    }

    public ProgressButton setText(String text) {
        this.text = text;
        if (btnAction != null) {
            btnAction.setText(text != null ? text : "");
        }
        return this;
    }

    public String getText() {
        if (btnAction != null) {
            return btnAction.getText().toString();
        } else {
            return "";
        }
    }

    public ProgressButton setBackground(int drawable) {
        if (btnAction != null) {
            btnAction.setBackgroundResource(drawable);
            ivStatus.setBackgroundResource(drawable);
            pBar.setBackgroundResource(drawable);
        }
        return this;
    }

    public ProgressButton setDisableButton() {
        if (btnAction != null) {
            return setBackground(R.drawable.pb_bg_button_disable);
        }
        return this;
    }

    public ProgressButton setEnableButton() {
        if (btnAction != null) {
            return setBackground(R.drawable.pb_bg_button);
        }
        return this;
    }


    //================================================================================
    // Action
    //================================================================================
    public void startProgress() {
        hideButtonAction();
    }

    public void revertSuccessProgress(final Listener listener) {
        Handler handler = new Handler();
        Runnable runnableRevert = new Runnable() {
            @Override
            public void run() {
                listener.onAnimationCompleted();
            }
        };
        revertSuccessProgress();
        handler.postDelayed(runnableRevert, BUTTON_PROGRESS_TIME);
    }

    public void revertProgress() {
        revertProgress(null);
    }
    public void revertProgress(String buttonText) {
        if(!TextUtils.isEmpty(buttonText)){
            setText(buttonText);
        }
        showButtonAction();
    }
    public void revertSuccessProgress() {
        revertSuccessProgress(false);
    }

    public void revertSuccessProgress(Boolean btnVisibility) {
        hideProgressBar();
        if (btnVisibility) {
            showButtonAction();
        } else {
            showSuccessView();
        }
    }


    public void performClick() {
        btnAction.performClick();
    }

    public void executeTask() {
        btnAction.performClick();
    }

    public ProgressButton setExpandCollapseTime(long expandCollapseTime) {
        this.expandCollapseTime = expandCollapseTime;
        return this;
    }

    //================================================================================
    // Utility
    //================================================================================


    private void showSuccessView() {
        if (ivStatus != null) {
//            ivStatus.startAnimation(inAnim);
//            ivStatus.setVisibility(View.VISIBLE);
            mAnim.startAlphaAnimation(ivStatus, View.VISIBLE);
        }
    }

    private void showProgressBar() {
        if (pBar != null) {
//            pBar.startAnimation(inAnim);
            mAnim.startAlphaAnimation(pBar, 10, View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (pBar != null) {
//            pBar.startAnimation(outAnim);
//            pBar.setVisibility(View.GONE);
            mAnim.startAlphaAnimation(pBar, 10, View.GONE);
        }
    }

    private AnimatorSet mAnimatorSet;

    private void showButtonAction() {
        if (btnAction != null) {
            int fromWidth, toWidth;
            fromWidth = mOriginalHeight;
            toWidth = mOriginalWidth;

            ValueAnimator heightAnimation = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight);
            heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.height = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.width = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.setDuration(expandCollapseTime);
            mAnimatorSet.playTogether(
                    heightAnimation, widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    hideProgressBar();
                    btnAction.setText("");
                    btnAction.setVisibility(View.VISIBLE);
                    if(mProgressListener != null){
                        mProgressListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    btnAction.setText(text);
                    btnAction.setVisibility(View.VISIBLE);
                    btnAction.setEnabled(true);
//                    ivStatus.setVisibility(View.GONE);
                    mAnim.startAlphaAnimation(ivStatus, View.GONE);
                    if(mProgressListener != null){
                        mProgressListener.onAnimationEnd(animation);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            hideProgressBar();
            btnAction.setVisibility(View.VISIBLE);
//            ivStatus.setVisibility(View.GONE);
            mAnim.startAlphaAnimation(ivStatus, View.GONE);

            mAnimatorSet.start();
        }
    }

    private void hideButtonAction() {
        if (btnAction != null) {
            btnAction.setEnabled(false);
            int fromWidth, toWidth;
            fromWidth = mOriginalWidth;
            toWidth = mOriginalHeight;

            ValueAnimator heightAnimation = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight);
            heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.height = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });

            ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = btnAction.getLayoutParams();
                    layoutParams.width = val;
                    btnAction.setLayoutParams(layoutParams);
                }
            });
            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.setDuration(expandCollapseTime);
            mAnimatorSet.playTogether(
                    heightAnimation, widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    btnAction.setText("");
                    showProgressBar();
                    if(mProgressListener != null){
                        mProgressListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    btnAction.setVisibility(View.GONE);
                    if(mProgressListener != null){
                        mProgressListener.onAnimationEnd(animation);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            mAnimatorSet.start();
        }
    }

    public ProgressButton setVisibility(int visibility) {
        if (btnAction != null) {
            btnAction.setVisibility(visibility);
        }
        return this;
    }


    private Drawable getDrawableRes(Context context, int resource) {
        return context.getResources().getDrawable( resource);
    }

    private int getColorRes(Context context, int resource) {
        return context.getResources().getColor(resource);
    }

    public ProgressButton addProgressListener(ProgressButton.AnimatorListener listener){
        this.mProgressListener = listener;
        if(mAnimatorSet != null && !mAnimatorSet.isRunning()){
            if (mProgressListener != null) {
                mProgressListener.onAnimationEnd(null);
            }
        }
        return this;
    }
}
