package com.example.angluswang.a2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

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

        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h)-10)/4;
        addCard(cardWidth, cardWidth);

        startGame();
    }

    private void addCard(int cardWidth, int cardHeight) {

        Card c;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                c = new Card(getContext());
                c.setNumber(0);
                addView(c, cardWidth, cardHeight);

                cardsMap[i][j] = c;
            }
        }
    }

    private void startGame() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cardsMap[i][j].setNumber(0);
            }
        }

        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {

        emptyPoints.clear();

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
               if (cardsMap[i][j].getNumber() <= 0) {
                    emptyPoints.add(new Point(i, j));
               }
            }
        }

        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardsMap[p.x][p.y].setNumber(Math.random()>0.1 ? 2:4);
    }

    private void swipeLeft() {

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x+1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNumber() > 0) {

                        if (cardsMap[x][y].getNumber() <= 0) {
                            cardsMap[x][y].setNumber(cardsMap[x1][y].getNumber());
                            cardsMap[x1][y].setNumber(0);

                            x--;
                            break;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNumber(cardsMap[x][y].getNumber()*2);
                            cardsMap[x1][y].setNumber(0);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void swipeRight() {

        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {

                for (int x1 = x-1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNumber() > 0) {

                        if (cardsMap[x][y].getNumber() <= 0) {
                            cardsMap[x][y].setNumber(cardsMap[x1][y].getNumber());
                            cardsMap[x1][y].setNumber(0);

                            x++;
                            break;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNumber(cardsMap[x][y].getNumber()*2);
                            cardsMap[x1][y].setNumber(0);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void swipeUp() {

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y+1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNumber() > 0) {

                        if (cardsMap[x][y].getNumber() <= 0) {
                            cardsMap[x][y].setNumber(cardsMap[x][y1].getNumber());
                            cardsMap[x][y1].setNumber(0);

                            y--;
                            break;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNumber(cardsMap[x][y].getNumber()*2);
                            cardsMap[x][y1].setNumber(0);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void swipeDown() {

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {

                for (int y1 = y-1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNumber() > 0) {

                        if (cardsMap[x][y].getNumber() <= 0) {
                            cardsMap[x][y].setNumber(cardsMap[x][y1].getNumber());
                            cardsMap[x][y1].setNumber(0);

                            y++;
                            break;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNumber(cardsMap[x][y].getNumber()*2);
                            cardsMap[x][y1].setNumber(0);
                            break;
                        }
                    }
                }
            }
        }

    }

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();
}
