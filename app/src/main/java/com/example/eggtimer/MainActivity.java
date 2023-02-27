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
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button goButton;
    boolean isCounterActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        runApp();
    }

    public void initView(){
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.countdownTextView);
        goButton = findViewById(R.id.goButton);


        timerTextView.setText("0:30");

        timerSeekBar.setMax(600);
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

    public void updateTimer(int secondsLeft){
        Log.i("updateTimer", "abc "+ secondsLeft);
        int minutes = secondsLeft/60;
        int seconds = secondsLeft % 60;

        String seconds_ = String.format("%02d", seconds);

        timerTextView.setText(minutes + ":"+seconds_);
    }

    public void resetTimer(){
        isCounterActive = false;
        timerTextView.setText("0:30");
        goButton.setText("GO!");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
    }

    public void startTimer(){
        isCounterActive = true;
        goButton.setText("STOP!");
        timerSeekBar.setEnabled(false);

        countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Log.i("Finished", "Timer all done");
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mediaPlayer.start();
            }
        }.start();
    }

    public void runApp(){
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCounterActive){
                    resetTimer();
                }else {
                    startTimer();
                }
            }
        });

    }
}