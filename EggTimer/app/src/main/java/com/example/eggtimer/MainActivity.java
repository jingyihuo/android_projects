package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;  // add
    Button goButton;  // add
    CountDownTimer counterDownTimer;

    public void reset(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        counterDownTimer.cancel();
        goButton.setText("Go");
        counterIsActive = false;
    }

    public void buttonClicked(View view){
        // Log.i("Button pressed", "Nice!");
        if(counterIsActive) {
            // reset everything
            reset();
            return;
        }

        counterIsActive = true;
        timerSeekBar.setEnabled(false);  // seek bar 不能使用
        goButton.setText("STOP!");

        counterDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int)millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                // Log.i("Finished", "Timer all done");
                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mplayer.start();
                reset();
            }
        }.start();
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        // 280s = 4 minutes : 40s

        String secondString = Integer.toString(seconds);

        if(secondString.length() < 2) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get all the views by id
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.countDownTextView);
        goButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(180); // 10 minutes = 600 seconds
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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