package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout gameLayout;
    Button goButton;
    Button playAgainButton;
    TextView sumTextView;
    TextView resultTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    // reset fields
    TextView scoreTextView;
    TextView timerTextView;

    List<Integer> answers = new ArrayList<>();
    int posCorrect;
    int score = 0;
    int count = 0;

    // goButtton onClick
    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        play();
    }

    // four options onClick
    public void chooseAnswer(View view){
        if(Integer.toString(posCorrect).equals(view.getTag().toString())) {
            resultTextView.setText("Correct!");
            score++;
        } else {
            resultTextView.setText("Wrong :(");
        }
        count++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(count));
        newQuestion();
    }

    // playAgain onClick
    public void playAgain(View view){
        play();
    }

    public void play(){
        playAgainButton.setVisibility(View.INVISIBLE);
        score = 0;
        count = 0;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(count));
        timerTextView.setText("30s");
        resultTextView.setText("");
        newQuestion();
        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                String t = String.valueOf(millisUntilFinished/1000) + "s";
                timerTextView.setText(t);
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                goButton.setText("Play Again!");
                goButton.setVisibility(View.VISIBLE);
                gameLayout.setVisibility(View.INVISIBLE);
                // playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        timerTextView = findViewById(R.id.timerTextView);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);


        // set gobutton
        goButton.setVisibility(View.VISIBLE);
        // set gameLayout
        gameLayout.setVisibility(View.INVISIBLE);
    }

    public void newQuestion(){
        setSumTextView();
        setButtons();
    }

    public void setSumTextView(){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        answers.clear();  // 每次都要擦除

        posCorrect = rand.nextInt(4);
        for(int i = 0; i < 4; i++) {
            if(i == posCorrect) {
                answers.add(a + b);
            } else {
                int incorrect = rand.nextInt(41);
                while(incorrect == a + b) {
                    incorrect = rand.nextInt(41);
                }
                answers.add(incorrect);
            }
        }
    }

    public void setButtons(){
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
}