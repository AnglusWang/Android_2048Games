package com.example.angluswang.a2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

/**
 * Created by Jeson on 2016/5/28.
 * 自定义GridLayout控件
 */

public class GameView extends GridLayout {

    public GameView(Context context) {
        super(context);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initGameView();
    }

    private void initGameView() {

        setOnTouchListener(new OnTouchListener() {

            private float startX, startY, offSetX, offSetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offSetX = event.getX() - startX;
                        offSetY = event.getY() - startY;
                        if (Math.abs(offSetX) > Math.abs(offSetY)) {
                            if (offSetX < -5) {
                                swipeLeft();
                            }else if(offSetX > 5) {
                                swipeRight();
                            }
                        }else {
                            if (offSetY < -5) {
                                swipeUp();
                            }else if(offSetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    private void swipeLeft() {

    }

    private void swipeRight() {

    }

    private void swipeUp() {

    }

    private void swipeDown() {

    }
}
