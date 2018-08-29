package com.mistershorr.uselessmachine;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Button buttonSelfDestruct;
    private Switch switchUseless;
    private ConstraintLayout constraintLayout;

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();
        setListeners();
    }

    private void setListeners() {
        buttonSelfDestruct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSelfDestructSequence();
            }
        });

        switchUseless.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,
                                         boolean checked) {
                if(checked) {
                    startSwitchOffTimer();
//                    Toast.makeText(MainActivity.this,
//                            "On", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startSelfDestructSequence() {
        // Disable the button
        buttonSelfDestruct.setEnabled(false);
        // Start a 10 second countdown timer that updates
        // the display every second
        new CountDownTimer(10000,10) {
            private long lastTime = 10000;
            private long duration = 500;
            private boolean red = false;

            @Override
            public void onTick(long millisUntilFinished) {
                // Want the button to show the countdown
                // Destruct in 10...
                // Destruct in 9...
                buttonSelfDestruct.setText("Destruct in "
                        + millisUntilFinished/1000);
                if(lastTime - millisUntilFinished > duration) {
                    if(!red) {
                        constraintLayout.setBackgroundColor(Color.rgb(255,0,0));
                        red = true;
                    } else {
                        constraintLayout.setBackgroundColor(Color.rgb(255,255,255));
                        red = false;
                    }
                    lastTime = millisUntilFinished;
                }

                if(millisUntilFinished > 5000) {
                    duration = 500;
                } else if(millisUntilFinished > 2500) {
                    duration = 250;
                } else {
                    duration = 100;
                }
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();


    }

    private void startSwitchOffTimer() {
        new CountDownTimer(5000, 100) {
            @Override
            public void onTick(long millisUntilFinish) {
                if(!switchUseless.isChecked()) {
                    //Log.d(TAG, "onTick: canceling");
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                switchUseless.setChecked(false);
                //Log.d(TAG, "onFinish: switch set to false");
            }
        }.start();
    }

    private void wireWidgets() {
        buttonSelfDestruct = findViewById(R.id.button_main_selfdestruct);
        switchUseless = findViewById(R.id.switch_main_useless);
        constraintLayout = findViewById(R.id.layout_main);
    }
}
