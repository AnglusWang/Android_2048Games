package com.example.angluswang.a2048;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Jeson on 2016/5/28.
 *
 */

public class Card extends FrameLayout {

    private TextView label;

    public Card(Context context) {
        super(context);

        label = new TextView(getContext());
        label.setTextSize(36);

        LayoutParams lp = new LayoutParams(-1, -1);
        addView(label, lp);

        setNumber(0);
    }

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;

        label.setText(number + "");
    }

    public boolean equals(Card c) {
        return getNumber() == c.getNumber();
    }
}