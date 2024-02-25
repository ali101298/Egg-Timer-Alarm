package com.eggTimer.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timeTextView;
    Button goButton;
    Boolean counterIsActive = false;
    Boolean musicIsPlaying = false;
    CountDownTimer countDownTimer;
    MediaPlayer mplayer;
    View b;


    public void resetTimer() {
        timeTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counterIsActive = false;
    }

    public void okButtonClicked(View view) {

        mplayer.stop();
        resetTimer();
        b.setVisibility(View.INVISIBLE);
        goButton.setVisibility(View.VISIBLE);
    }


    public void buttonClicked(View view) {

        if (counterIsActive) {

            resetTimer();

        } else {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");


            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    mplayer = MediaPlayer.create(getApplicationContext(), R.raw.annoying_alarm);
                    mplayer.start();
                    musicIsPlaying = true;

                    if(musicIsPlaying) {

                        goButton.setVisibility(View.INVISIBLE);
                        b = findViewById(R.id.okButton);
                        b.setVisibility(View.VISIBLE);
                    }
                }
            }.start();

        }
    }


    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timeTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerSeekBar = findViewById(R.id.timerSeekBar);
        timeTextView = findViewById(R.id.countDownTextView);
        goButton = findViewById(R.id.startButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}