package com.example.connect5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; // 0 for lion, 1 for kitten
    int N = 5;
    int[] rows = {0, 0, 0, 0, 0};
    int[] cols = {0, 0, 0, 0, 0};
    int[] diag = {0, 0};

    public void dropIn(View view){

        ImageView counter = (ImageView) view;

        int pos = Integer.parseInt(counter.getTag().toString());
        int row = pos / N;
        int col = pos % N;
        int currPlayer = activePlayer;
        counter.setTranslationY(-1000);
        // set image
        if(activePlayer == 0) {
            counter.setImageResource(R.drawable.lion);
            activePlayer = 1;
        } else {
            counter.setImageResource(R.drawable.kitten);
            activePlayer = 0;
        }
        counter.animate().translationYBy(1000).rotation(3600).setDuration(300);
        if(win(row, col, currPlayer)) {
            String winner = currPlayer == 0 ? "The Great Lion" : "The Little Kitten";

            TextView textView = (TextView) findViewById(R.id.winnerMessage);
            textView.setText(winner + " has won!");

            Button button = (Button) findViewById(R.id.button);

            textView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void playAgain(View view) {
        TextView textView = (TextView) findViewById(R.id.winnerMessage);
        Button button = (Button) findViewById(R.id.button);
        textView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        // reset the grid and the counter
        // loop through the grid of the image views: android loop through all objects in grid layout
        GridLayout myGrid = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<myGrid.getChildCount(); i++) {
            ImageView counter = (ImageView) myGrid.getChildAt(i);
            counter.setImageDrawable(null);
        }

        // initialize
        activePlayer = 0; // 0 for yellow, 1 for red

        for(int i = 0; i < N; i++){
            rows[i] = 0;
            cols[i] = 0;
        }

        for(int i = 0; i < 2; i++) {
            diag[i] = 0;
        }

    }

    private boolean win(int row, int col, int player) {
        if(player == 0) { // add

            rows[row]++;
            if(rows[row] == N) return true;
            cols[col]++;
            if(cols[col] == N) return true;

            if(row == col) {
                diag[0]++;
                if(diag[0] == N) return true;
            }

            if(row + col == N - 1) {
                diag[1]++;
                if(diag[1] == N) return true;
            }

        } else {

            rows[row]--;
            if(rows[row] == -N) return true;
            cols[col]--;
            if(cols[col] == -N) return true;

            if(row == col) {
                diag[0]--;
                if(diag[0] == -N) return true;
            }

            if(row + col == N - 1) {
                diag[1]--;
                if(diag[1] == -N) return true;
            }
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}