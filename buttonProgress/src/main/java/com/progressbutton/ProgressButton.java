package com.progressbutton;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.progressbutton.base.ProgressButtonClass;


/**
 * Created by Admin on 8/28/2017.
 */
public interface ProgressButton {

    interface Listener {
        void onAnimationCompleted();
    }

    interface ClickListener {
        void onClicked(View view);
    }

    interface AnimatorListener {
        default void onAnimationStart(Animator animation) {}
        void onAnimationEnd(Animator animation);
    }

    static ProgressButton newInstance(Activity activity) {
        return ProgressButtonClass.newInstance(activity, activity.getWindow().getDecorView().getRootView());
    }

    static ProgressButton newInstance(Context context, View v) {
        return ProgressButtonClass.newInstance(context, v);
    }

    //================================================================================
    // Accessors
    //================================================================================
    ProgressButton setOnClickListener(ClickListener clickListener);


    ProgressButton setOnEditorActionListener(EditText editText);

    ProgressButton setOnEditorActionListener(EditText editText, String btnLabel);

    ProgressButton setTransparentBackground(int textColor);

    Button getButton();

    ProgressButton setText(String text);

    String getText();

    ProgressButton setBackground(int drawable);

    ProgressButton setDisableButton();

    ProgressButton setEnableButton();


    //================================================================================
    // Action
    //================================================================================
    void startProgress();

    void startProgress(Listener listener);

    void revertProgress();

    void revertProgress(Listener listener);

    void revertProgress(String buttonText, Listener listener);

    void revertSuccessProgress();

    void revertSuccessProgress(final Listener listener);

    void revertSuccessProgress(Boolean btnVisibility, Listener listener);

    void performClick();

    void executeTask();

    ProgressButton setExpandCollapseTime(long expandCollapseTime);

    //================================================================================
    // Utility
    //================================================================================

    ProgressButton setVisibility(int visibility);

    ProgressButton setStopClickProgress(boolean isStopClickProgress);

    ProgressButton addProgressListener(ProgressButton.AnimatorListener listener);
}
