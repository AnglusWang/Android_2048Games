package com.example.angluswang.a2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    public MainActivity() {
        sMainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvScore = (TextView) findViewById(R.id.tvScore);

        gameView = (GameView) findViewById(R.id.gameView);

        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.startGame();
        }});
    }

    public void clearScore() {
        mScore = 0;
    }

    public void showScore() {
        mTvScore.setText(mScore + "");
    }

    public void addScore(int score) {
        mScore += score;
        showScore();
    }

    private int mScore;
    private TextView mTvScore;
    private Button btnNewGame;
    private GameView gameView;

    private static MainActivity sMainActivity = null;

    public static MainActivity getMainActivity() {
        return sMainActivity;
    }
}
