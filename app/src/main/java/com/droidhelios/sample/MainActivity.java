package com.droidhelios.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.progressbutton.ProgressButton;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    private ProgressButton btnAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAction = ProgressButton.newInstance(this)
                .setText("Login for your Account")
                .setOnClickListener(new ProgressButton.ClickListener() {
                    @Override
                    public boolean onValidate() {
                        return false;
                    }

                    @Override
                    public void onClicked(View view) {
                        startTask();
                    }
                });
    }

    private int count = 0;

    private void startTask() {
        //use this method to initiate progress bar while starting task in background
//        btnAction.startProgress();

        TaskRunner.getInstance().executeAsync(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                count+=1;
//                Thread.sleep(3000);
                return count == 3;
            }
        }, new TaskRunner.Callback<Boolean>() {
            @Override
            public void onComplete(Boolean result) {
                if(result){
                    // call this method when getting success response from background task
                    btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                        @Override
                        public void onAnimationCompleted() {
                            btnAction.getButton().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btnAction.revertProgress();
                                }
                            }, 400);
                        }
                    });
                }else {
                    Toast.makeText(MainActivity.this, "Retry for your acc", Toast.LENGTH_SHORT).show();
                    // use this method when getting wrong response and revert the initial stage of button
                    btnAction.setText("Retry for your acc");
                    btnAction.revertProgress();
                }
            }
        });
    }
}