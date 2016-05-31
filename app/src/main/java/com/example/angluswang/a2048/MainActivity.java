package com.example.angluswang.a2048;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.angluswang.a2048.R.id.tvScore;

public class MainActivity extends Activity {

    public MainActivity() {
        sMainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvScore = (TextView) findViewById(tvScore);
        mTvBestScore = (TextView) findViewById(R.id.tvBestScore);

        gameView = (GameView) findViewById(R.id.gameView);

        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.startGame();
                mTvScore.setText("0");
        }});

        mAnimPlayer = (AnimPlayer) findViewById(R.id.animPlayer);
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

        int maxScore = Math.max(mScore, getBestScore());
        saveBestScore(maxScore);
        showBestScore(maxScore);
    }

    public void saveBestScore(int s){
        SharedPreferences.Editor e = getPreferences(MODE_PRIVATE).edit();
        e.putInt(SP_KEY_BEST_SCORE, s);
        e.commit();
    }

    public int getBestScore(){
        return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
    }

    public void showBestScore(int s){
        mTvBestScore.setText(s+"");
    }

    public AnimPlayer getAnimLayer() {
        return mAnimPlayer;
    }

    private int mScore = 0;
    private TextView mTvScore, mTvBestScore;
    private Button btnNewGame;
    private GameView gameView;
    private AnimPlayer mAnimPlayer;

    public static final String SP_KEY_BEST_SCORE = "bestScore";

    private static MainActivity sMainActivity = null;

    public static MainActivity getMainActivity() {
        return sMainActivity;
    }
}
