package com.study.ssumnow;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Created by hhylu on 2016-03-04.
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mCardStack.dd.getMStarted()) {
           return false;
        }

        super.onTouchEvent(ev);
    }
}
