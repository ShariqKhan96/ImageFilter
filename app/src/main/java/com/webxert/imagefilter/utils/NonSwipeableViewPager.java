package com.webxert.imagefilter.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by hp on 8/6/2018.
 */

public class NonSwipeableViewPager extends ViewPager {

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    public NonSwipeableViewPager(@NonNull Context context) {
        super(context);

        settingUpScroller();
    }

    public NonSwipeableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        settingUpScroller();
    }

    private void settingUpScroller() {

        try {
            Class<?> viewPager = ViewPager.class;
            Field scroller = viewPager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (NoSuchFieldException e) {

            Log.e("FieldException", e.getMessage());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    private class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 400);
        }
    }
}
