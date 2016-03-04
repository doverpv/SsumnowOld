package com.study.ssumnow;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.study.cardstack.DragGestureDetector;

/**
 * Created by hhylu on 2016-03-04.
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (DragGestureDetector.getMStarted()) {
           return false;
        }

        super.onTouchEvent(ev);
        return true;
    }
}
