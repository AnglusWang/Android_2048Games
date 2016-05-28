package com.example.angluswang.a2048;

import android.app.Activity;
import android.os.Bundle;
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

    private static MainActivity sMainActivity = null;

    public static MainActivity getMainActivity() {
        return sMainActivity;
    }
}
