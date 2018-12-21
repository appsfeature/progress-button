package com.progressbutton;

public class Usage {


    //================================================================================
    // Usage in xml
    //================================================================================

    /**
     *     <include layout="@layout/button_progress"/>
     */

    //================================================================================
    // Initialization method
    //================================================================================

    /**
     *  btnAction = ProgressButton.newInstance(this, getActivityRootView())
     *                 .setText("Send Request")
     *                 .setBackground(R.drawable.bg_button_disable)
     *                 .setOnClickListener(new View.OnClickListener() {
     *                     @Override
     *                     public void onClick(View v) {
     *                         executeTask();
     *                     }
     *                 });
     */

    //================================================================================
    // Update progress bar methods
    //================================================================================

    /**
     *    //use this method to initiate progress bar while starting task in background
     *    btnAction.startProgress();
     *
     *    // call this method when getting success response from background task
     *    btnAction.revertSuccessProgress(new ProgressButton.Listener() {
     *                     @Override
     *                     public void onAnimationCompleted() {
     *                     }
     *                 });
     *    // use this method when getting wrong response and revert the initial stage of button
     *    btnAction.revertProgress();
     *
     */

}
