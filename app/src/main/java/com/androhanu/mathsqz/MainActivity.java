package com.androhanu.mathsqz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button start, button1, button2, button3, button4;
    TextView timer, question, tv_score, result;
    ProgressBar progressBar;
    game g = new game();
    int timeRemaining = 30;
    CountDownTimer countDownTimer = new CountDownTimer(30000 , 1000) {
        @Override
        public void onTick(long l) {
            timeRemaining--;
            timer.setText(Integer.toString(timeRemaining)+ "sec");
            progressBar.setProgress(30-timeRemaining);

        }

        @Override
        public void onFinish() {
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            result.setText("TIME IS UP " + g.getNumberCorrect()+ "/" + (g.getTotalQuestions()-1));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    start.setVisibility(View.VISIBLE);
                    result.setText(("Play Again"));

                }
            }, 4000);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        button1 = findViewById(R.id.btans1);
        button2 = findViewById(R.id.btans2);
        button3 = findViewById(R.id.btans3);
        button4 = findViewById(R.id.btans4);

        timer = findViewById(R.id.timer);
        question = findViewById(R.id.question);
        tv_score = findViewById(R.id.tvscore);
        result = findViewById(R.id.result);

        progressBar = findViewById(R.id.progressBar);


        timer.setText("0 sec");
        question.setText("");
        result.setText("press start");
        tv_score.setText("0 pts");

        View.OnClickListener startButtonClickListener =  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button startButton = (Button) view;

                startButton.setVisibility(View.INVISIBLE);
                timeRemaining =30;
                g = new game();
                nextTurn();
                countDownTimer.start();



            }
        };

        start.setOnClickListener(startButtonClickListener);

        View.OnClickListener answerButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button buttonClicked = (Button) view;
                try {
                    int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                    g.checkAnswer(answerSelected);
                    tv_score.setText(Integer.toString(g.getScore()));
                    nextTurn();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "click start !", Toast.LENGTH_SHORT).show();
                }


            }
        };

        button1.setOnClickListener(answerButtonOnClickListener);
        button2.setOnClickListener(answerButtonOnClickListener);
        button3.setOnClickListener(answerButtonOnClickListener);
        button4.setOnClickListener(answerButtonOnClickListener);
    }


    public void nextTurn (){
        g.makeNewQuestion();
        int [] answer = g.getCurrentQuestion().getAnswerArray();
        button1.setText(Integer.toString(answer[0]));
        button2.setText(Integer.toString(answer[1]));
        button3.setText(Integer.toString(answer[2]));
        button4.setText(Integer.toString(answer[3]));

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);

        question.setText(g.getCurrentQuestion().getQuestionPhrase());

        result.setText(g.getNumberCorrect()+ "/" + (g.getTotalQuestions()-1));

    }
}