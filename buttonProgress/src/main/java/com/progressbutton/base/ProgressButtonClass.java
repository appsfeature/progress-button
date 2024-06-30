package com.progressbutton.base;

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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.progressbutton.ProgressButton;
import com.progressbutton.R;


/**
 * Created by Admin on 8/28/2017.
 */
public class ProgressButtonClass implements View.OnClickListener, ProgressButton{

    //================================================================================
    // Properties
    //================================================================================
    private final ProgressAnim mAnim;
    private long expandCollapseTime = 400;
    private final ProgressBar pBar;
    private final Button btnAction;
    private final ImageView ivStatus;
    private final Context context;
    private int mOriginalWidth, mOriginalHeight;
    private String text;
    private ProgressButton.AnimatorListener mProgressListener;
    private ProgressButton.ClickListener mClickListener;
    private boolean isStopClickProgress = false;


    public static ProgressButtonClass newInstance(Activity activity) {
        return new ProgressButtonClass(activity, activity.getWindow().getDecorView().getRootView());
    }

    public static ProgressButtonClass newInstance(Context context, View v) {
        return new ProgressButtonClass(context, v);
    }

    private ProgressButtonClass(Context context, View view) {
        this.context = context;
        this.mAnim = new ProgressAnim();
        ivStatus = view.findViewById(R.id.btp_iv_status);
        pBar = view.findViewById(R.id.btp_progress);
        btnAction = view.findViewById(R.id.btp_btn_action);
        btnAction.post(new Runnable() {
            @Override
            public void run() {
                mOriginalWidth = btnAction.getWidth();
                mOriginalHeight = btnAction.getHeight();
            }
        });
        btnAction.setOnClickListener(this);
    }


    @Override
    public ProgressButtonClass setOnClickListener(ProgressButton.ClickListener clickListener) {
        this.mClickListener = clickListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        if(isStopClickProgress){
            if (mClickListener != null) {
                mClickListener.onClicked(view);
            }
        }else {
            startProgress(new ProgressButton.Listener() {
                @Override
                public void onAnimationCompleted() {
                    if (mClickListener != null) {
                        mClickListener.onClicked(view);
                    }
                }
            });
        }
    }

    @Override
    public ProgressButtonClass setOnEditorActionListener(EditText editText) {
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

    @Override
    public ProgressButtonClass setOnEditorActionListener(EditText editText, String btnLabel) {
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

    @Override
    public ProgressButtonClass setTransparentBackground(int textColor) {
        if (btnAction != null) {
            btnAction.setBackgroundResource(getColorRes(context, android.R.color.transparent));
            pBar.setIndeterminateDrawable(getDrawableRes(context, R.drawable.pb_progress_custom_accent));
            ivStatus.setBackgroundResource(getColorRes(context, android.R.color.transparent));
            ivStatus.setColorFilter(getColorRes(context, R.color.colorAccent));
            btnAction.setTextColor(textColor);
        }
        return this;
    }

    @Override
    public Button getButton() {
        return btnAction;
    }

    @Override
    public ProgressButtonClass setText(String text) {
        this.text = text;
        if (btnAction != null) {
            btnAction.setText(text != null ? text : "");
        }
        return this;
    }

    @Override
    public String getText() {
        if (btnAction != null) {
            return btnAction.getText().toString();
        } else {
            return "";
        }
    }

    @Override
    public ProgressButtonClass setBackground(int drawable) {
        if (btnAction != null) {
            btnAction.setBackgroundResource(drawable);
            ivStatus.setBackgroundResource(drawable);
            pBar.setBackgroundResource(drawable);
        }
        return this;
    }

    @Override
    public ProgressButtonClass setDisableButton() {
        if (btnAction != null) {
            return setBackground(R.drawable.pb_bg_button_disable);
        }
        return this;
    }

    @Override
    public ProgressButtonClass setEnableButton() {
        if (btnAction != null) {
            return setBackground(R.drawable.pb_bg_button);
        }
        return this;
    }


    private final Handler handler = new Handler();

    private boolean isProgressAnimationCompleted = false;

    @Override
    public void startProgress() {
        isProgressAnimationCompleted = false;
        startProgress(null);
        handler.postDelayed(() -> {
            isProgressAnimationCompleted = true;
        }, 2000);
    }

    @Override
    public void startProgress(ProgressButton.Listener listener) {
        hideButtonAction(listener);
    }

    @Override
    public void revertProgress() {
        if(isProgressAnimationCompleted) {
            revertProgress(null);
        }else {
            handler.postDelayed(() -> revertProgress(null), 1000);
        }
    }

    @Override
    public void revertProgress(ProgressButton.Listener listener) {
        revertProgress(null, listener);
    }

    @Override
    public void revertProgress(String buttonText, ProgressButton.Listener listener) {
        if(!TextUtils.isEmpty(buttonText)){
            setText(buttonText);
        }
        showButtonAction(listener);
    }

    @Override
    public void revertSuccessProgress() {
        revertSuccessProgress(false, null);
    }

    @Override
    public void revertSuccessProgress(final ProgressButton.Listener listener) {
        revertSuccessProgress(false, listener);
    }

    @Override
    public void revertSuccessProgress(Boolean btnVisibility, ProgressButton.Listener listener) {
        hideProgressBar();
        if (btnVisibility) {
            showButtonAction(listener);
        } else {
            showSuccessView(listener);
        }
    }

    @Override
    public void performClick() {
        btnAction.performClick();
    }

    @Override
    public void executeTask() {
        btnAction.performClick();
    }

    @Override
    public ProgressButtonClass setExpandCollapseTime(long expandCollapseTime) {
        this.expandCollapseTime = expandCollapseTime;
        return this;
    }

    //================================================================================
    // Utility
    //================================================================================


    private void showSuccessView(ProgressButton.Listener listener) {
        if (ivStatus != null) {
//            ivStatus.startAnimation(inAnim);
//            ivStatus.setVisibility(View.VISIBLE);
            mAnim.startAlphaAnimation(ivStatus, View.VISIBLE, listener);
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

    private void showButtonAction(ProgressButton.Listener listener) {
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
                    if(listener != null){
                        listener.onAnimationCompleted();
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

    private void hideButtonAction(ProgressButton.Listener listener) {
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
                    if(listener != null){
                        listener.onAnimationCompleted();
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

    @Override
    public ProgressButtonClass setVisibility(int visibility) {
        if (btnAction != null) {
            btnAction.setVisibility(visibility);
        }
        return this;
    }

    @Override
    public ProgressButtonClass setStopClickProgress(boolean isStopClickProgress) {
        this.isStopClickProgress = isStopClickProgress;
        return this;
    }


    private Drawable getDrawableRes(Context context, int resource) {
        return context.getResources().getDrawable( resource);
    }

    private int getColorRes(Context context, int resource) {
        return context.getResources().getColor(resource);
    }

    @Override
    public ProgressButtonClass addProgressListener(ProgressButton.AnimatorListener listener){
        this.mProgressListener = listener;
        if(mAnimatorSet != null && !mAnimatorSet.isRunning()){
            if (mProgressListener != null) {
                mProgressListener.onAnimationEnd(null);
            }
        }
        return this;
    }
}
