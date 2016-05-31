package com.example.angluswang.a2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        setColumnCount(Config.LINE);
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

        Config.CARD_WIDTH = (Math.min(w, h)-10)/ Config.LINE;
        addCard(Config.CARD_WIDTH, Config.CARD_WIDTH);

        startGame();
    }

    private void addCard(int cardWidth, int cardHeight) {

        Card c;
        for (int j = 0; j < Config.LINE; j++) {
            for (int i = 0; i < Config.LINE; i++) {
                c = new Card(getContext());
                c.setNumber(0);
                addView(c, cardWidth, cardHeight);

                cardsMap[i][j] = c;
            }
        }
    }

    public void startGame() {

        MainActivity aty = MainActivity.getMainActivity();
        aty.clearScore();
        aty.showBestScore(aty.getBestScore());

        for (int i = 0; i < Config.LINE; i++) {
            for (int j = 0; j < Config.LINE; j++) {
                cardsMap[i][j].setNumber(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {

        emptyPoints.clear();

        for (int j = 0; j < Config.LINE; j++) {
            for (int i = 0; i < Config.LINE; i++) {
               if (cardsMap[i][j].getNumber() <= 0) {
                    emptyPoints.add(new Point(i, j));
               }
            }
        }

        if (emptyPoints.size() > 0) {
            Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
            cardsMap[p.x][p.y].setNumber(Math.random()>0.1 ? 2:4);

            MainActivity.getMainActivity().getAnimLayer().createScaleTo1(cardsMap[p.x][p.y]);
        }
    }

    private void swipeLeft() {

        boolean merge = false;

        for (int y = 0; y < Config.LINE; y++) {
            for (int x = 0; x < Config.LINE; x++) {

                for (int x1 = x+1; x1 < Config.LINE; x1++) {
                    if (cardsMap[x1][y].getNumber() > 0) {

                        if (cardsMap[x][y].getNumber() <= 0) {
                            MainActivity.getMainActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);

                            cardsMap[x][y].setNumber(cardsMap[x1][y].getNumber());
                            cardsMap[x1][y].setNumber(0);

                            merge = true;
                            x--;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])) {
                            MainActivity.getMainActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);

                            cardsMap[x][y].setNumber(cardsMap[x][y].getNumber()*2);
                            cardsMap[x1][y].setNumber(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNumber());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeRight() {

        boolean merge = false;

        for (int y = 0; y < Config.LINE; y++) {
            for (int x = Config.LINE - 1; x >= 0; x--) {

                for (int x1 = x-1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNumber() > 0) {

                        if (cardsMap[x][y].getNumber() <= 0) {

                            MainActivity.getMainActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);

                            cardsMap[x][y].setNumber(cardsMap[x1][y].getNumber());
                            cardsMap[x1][y].setNumber(0);

                            merge = true;
                            x++;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])) {

                            MainActivity.getMainActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);

                            cardsMap[x][y].setNumber(cardsMap[x][y].getNumber()*2);
                            cardsMap[x1][y].setNumber(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNumber());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeUp() {

        boolean merge = false;

        for (int x = 0; x < Config.LINE; x++) {
            for (int y = 0; y < Config.LINE; y++) {

                for (int y1 = y+1; y1 < Config.LINE; y1++) {
                    if (cardsMap[x][y1].getNumber() > 0) {

                        if (cardsMap[x][y].getNumber() <= 0) {

                            MainActivity.getMainActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x][y1], cardsMap[x][y],x, x, y1, y);

                            cardsMap[x][y].setNumber(cardsMap[x][y1].getNumber());
                            cardsMap[x][y1].setNumber(0);

                            merge = true;
                            y--;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])) {

                            MainActivity.getMainActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x][y1], cardsMap[x][y],x, x, y1, y);

                            cardsMap[x][y].setNumber(cardsMap[x][y].getNumber()*2);
                            cardsMap[x][y1].setNumber(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNumber());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeDown() {

        boolean merge = false;

        for (int x = 0; x < Config.LINE; x++) {
            for (int y = Config.LINE - 1; y >= 0; y--) {

                for (int y1 = y-1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNumber() > 0) {

                        if (cardsMap[x][y].getNumber() <= 0) {

                            MainActivity.getMainActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);

                            cardsMap[x][y].setNumber(cardsMap[x][y1].getNumber());
                            cardsMap[x][y1].setNumber(0);

                            merge = true;
                            y++;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])) {

                            MainActivity.getMainActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);

                            cardsMap[x][y].setNumber(cardsMap[x][y].getNumber()*2);
                            cardsMap[x][y1].setNumber(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNumber());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void checkComplete() {

        boolean complete = true;

        All:
        for (int y = 0; y < Config.LINE; y++) {
            for (int x = 0; x < Config.LINE; x++) {
                if (cardsMap[x][y].getNumber()==0 ||
                        (x>0 && cardsMap[x][y].equals(cardsMap[x-1][y])) ||
                        (x<Config.LINE -1 && cardsMap[x][y].equals(cardsMap[x+1][y])) ||
                        (y>0 && cardsMap[x][y].equals(cardsMap[x][y-1])) ||
                        (y<Config.LINE - 1 && cardsMap[x][y].equals(cardsMap[x][y+1])) ) {

                    complete = false;
                    break All;
                }
            }
        }

        if (complete) {
            new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setTitle("你好")
                    .setMessage("游戏结束 Game Over~")
                    .setNegativeButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }

    private Card[][] cardsMap = new Card[Config.LINE][Config.LINE];
    private List<Point> emptyPoints = new ArrayList<>();
}
